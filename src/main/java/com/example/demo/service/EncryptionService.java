package com.example.demo.service;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionService {

    public byte[] decodeFromBase64(String dataBase64){
        return Base64.getDecoder().decode(dataBase64);
    }

    public String encodeToBase64(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    public PublicKey publicKeyFromBase64(String pkBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        byte[] decodedPK=Base64.getDecoder().decode(pkBase64);
        PublicKey publicKey=keyFactory.generatePublic(new X509EncodedKeySpec(decodedPK));
        return publicKey;
    }

    public PrivateKey privateKeyFromBase64(String pkBase64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        byte[] decodedPK=Base64.getDecoder().decode(pkBase64);
        PrivateKey privateKey=keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPK));
        return privateKey;
    }


    public String encryptRSA(byte[] data,PublicKey publicKey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] bytes=cipher.doFinal(data);
        return encodeToBase64(bytes);

    }

    public byte[] decryptRSA(String dataBase64,PrivateKey privateKey) throws Exception{
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decodedEncryptedData=decodeFromBase64(dataBase64);
        byte[] decryptedData=cipher.doFinal(decodedEncryptedData);
        return decryptedData;

    }

    public PublicKey publicKeyFromCertificate(String filename)throws Exception
    {
        FileInputStream fileInputStream=new FileInputStream(filename);
        CertificateFactory certificateFactory=CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
        System.out.println(certificate.toString());
        return certificate.getPublicKey();

    }

    public PrivateKey privateKeyFromJKS(String filename,String jksPassword,String alias)throws Exception
    {
        FileInputStream fileInputStream=new FileInputStream(filename);
        KeyStore keyStore=KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(fileInputStream,jksPassword.toCharArray());
        Key key = keyStore.getKey(alias, jksPassword.toCharArray());
        PrivateKey privateKey= (PrivateKey) key;
        return privateKey;


    }


    public String hmacSign(byte[] data,String privateSecret)throws Exception{
        SecretKeySpec secretKeySpec=new SecretKeySpec(privateSecret.getBytes(),"HmacSHA256");
        Mac mac=Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] signature =mac.doFinal(data);
        return Base64.getEncoder().encodeToString(signature);


    }

    public boolean hmacVerify(String signedDocument,String secret)throws Exception{
        SecretKeySpec secretKeySpec=new SecretKeySpec(secret.getBytes(),"HmacSHA256");
        Mac mac=Mac.getInstance("HmacSHA256");
        String[] splittedDocument=signedDocument.split("_.._");
        String document=splittedDocument[0];
        String documentSignature=splittedDocument[1];
        mac.init(secretKeySpec);
        byte[] sign =mac.doFinal(document.getBytes());
        String base64Sign=Base64.getEncoder().encodeToString(sign);
        return (base64Sign.equals(documentSignature));
    }

    public String rsaSign(byte[] data,PrivateKey privateKey) throws Exception{
        Signature signature=Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey,new SecureRandom());
        signature.update(data);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }






    public boolean rsaSignVerify(String signedDocument,PublicKey publicKey) throws Exception{
        Signature signature=Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        String[] data=signedDocument.split("_.._");
        String document=data[0];
        String sign=data[1];
        byte[] decodedSignature=Base64.getMimeDecoder().decode(sign);
        signature.update(document.getBytes());
        boolean verify=signature.verify(decodedSignature);
        return verify;
    }
}
