
package com.example.demo.controller;

import com.example.demo.dao.FileRepository;
import com.example.demo.dao.ProcessusRepository;
import com.example.demo.dao.ProcessusUserRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.*;
import com.example.demo.message.ResponseFile;
import com.example.demo.message.ResponseMessage;
import com.example.demo.service.EncryptionService;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.ProcessusService;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfTrueTypeFont;
import com.spire.pdf.security.PdfCertificate;
import com.spire.pdf.security.PdfSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
//import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStorageService storageService;

    @Autowired
    private ProcessusService processusService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcessusRepository processusRepository;

    @Autowired
    private ProcessusUserRepository processusUserRepository;

    private EncryptionService encryptionService;

//    private int x=280;
//    private int y=80;
    private int x;
    private int y;
    private int nbSign=0;
    static String signedFileName="";



    @GetMapping("/filesByProcessus")
    public List<String> findFilesByProcessus(@RequestParam Long id){
        return storageService.findFilesByProcessus(id);
    }





    @PostMapping("/signn/{email}/{filename1}")
    public ResponseEntity<ResponseMessage> signnFile(@PathVariable String email,@PathVariable String filename1) throws Exception {
    nbSign=0;

        String message = "";




        File fileFromProcessus = storageService.getFiless(filename1);
      File finalFile=storageService.getFiless("signed"+filename1);

        User user=userRepository.findByUsername(email);
        if(storageService.getFiless(filename1).getNbSignatures()==0)
        {
            x=280;
            y=50;
        }



//        String idFile=fileFromProcessus.getId();

//        File initialFileProcess=new File(idFile,fileFromProcessus.getType(),fileFromProcessus.getData(), fileFromProcessus.getNbSignatures());
//        String filename = fileFromProcessus.getName();
//        if(fileFromProcessus.getNbSignatures()==0)
//        signedFileName=filename;




        MultipartFile fErr = null;
        //System.out.println(new String(file.getBytes()));
        //storageService.store(file);


        PdfDocument doc = new PdfDocument();

//        File file = fileRepository.findByName_filee("2011.pdf");

        if(fileFromProcessus.getNbSignatures()==0)
        doc.loadFromFile("C://users//Asus//downloads//" + filename1);
        else
            doc.loadFromFile("C://users//Asus//downloads//" + "signed"+filename1);

        System.out.println("*************");
        System.out.println(doc.getPageSettings().getHeight());

        PdfCertificate cert = new PdfCertificate("C:\\Users\\ASUS\\Downloads\\"+user.getFirst_name()+user.getLast_name()+".pfx", "123456");
        PdfSignature signature = new PdfSignature(doc, doc.getPages().get(0), cert, "MySignature");
        Rectangle2D rect = new Rectangle2D.Float();
        rect.setFrame(new Point2D.Float((float) doc.getPages().get(0).getActualSize().getWidth() - x, (float) doc.getPages().get(0).getActualSize().getHeight() - y), new Dimension(270, 100));

        signature.setBounds(rect);

        signature.setNameLabel("Le signataire: ");
        signature.setDateLabel("Date:");
        signature.setDate(new java.util.Date());
//        signature.setReasonLabel (" reasons: ");
//        signature.setReason (" document owner ");
        signature.setDistinguishedNameLabel(" DN: ");
        signature.setDistinguishedName(signature.getCertificate().get_IssuerName().getName());

        signature.setSignDetailsFont(new PdfTrueTypeFont(new Font("Arial Unicode MS", Font.PLAIN, 9)));



        System.out.println("*********************");
        System.out.println("nbSignatures: " + fileFromProcessus.getNbSignatures());

//        if(fileFromProcessus.getNbSignatures()==0)
//            signedFileName="signed"+filename;
        doc.saveToFile("C://users//Asus//downloads//" + "signed"+filename1);
        FileSystemResource resource = new FileSystemResource("c://users//asus//Downloads//" + "signed"+filename1);

//        File fileDB = new File(filename, "application/pdf", resource.getInputStream().readAllBytes(), nbSign);
        MultipartFile f1 = new MockMultipartFile("file", "signed"+filename1, "application/pdf", resource.getInputStream().readAllBytes());
//        MultipartFile f0 = new MockMultipartFile("file", fileFromProcessus.getName(), "application/pdf", fileFromProcessus.getData());
//        File signedFile = new File(f1.getOriginalFilename(), f1.getContentType(), f1.getInputStream().readAllBytes(), nbSign);
//        System.out.println(signedFile.getName());
//        String idFile = storageService.getFiles(f1.getOriginalFilename());
        Long id_ProcessusFile = processusService.getProcessusByName("Processus_" + filename1);
        System.out.println("idProc1: " + id_ProcessusFile);
        Processus processus = processusRepository.getById(id_ProcessusFile);
        System.out.println("processname: " + processus.getName_processus());

        int nb=fileRepository.findNbSign(filename1);
        System.out.println("*********");
        System.out.println("nb courant: "+nb);
        if (nb >0)
        {nbSign=storageService.getFiless(filename1).getNbSignatures();

        }
        else
            nbSign=0;

        nbSign++;

        System.out.println("*********************");
        System.out.println("nbSign: "+nbSign);

        fileRepository.updateNbSign(filename1,nbSign);

        System.out.println("*********************");
        System.out.println("f1.name : "+f1.getOriginalFilename());

//        Long id_Processus=processusService.getProcessusByName("Processus_"+filename);
//        System.out.println("idProc: "+id_Processus);
//        Processus processus=processusRepository.getById(id_Processus);
//        System.out.println("idProc2: "+id_ProcessusFile);
//        Set<User> signataires=processus.getSignataires();
//
//        System.out.println("signataires: "+signataires.toArray().length);

//        fileFromProcessus.getNbSignatures()++;
//        File ff=new File()

        File f=processus.getInitialFile();
        System.out.println("initialFile: "+f.getName());


         Long nbSignataires=userRepository.countNbSigners(id_ProcessusFile);
        System.out.println("********************");
         System.out.println("nbSignataires: "+nbSignataires);

        Processus processusF;
        if(nbSignataires==nbSign) {
            storageService.store(f1,nbSign);
            String id1=storageService.getFiles(f1.getOriginalFilename());
            File file_processus=storageService.getFile(id1);
            processusF = new Processus(id_ProcessusFile, "Processus_" + filename1, processus.getEmission_date(), Status.TERMINÉ, f, file_processus);

        }
        else
            processusF=new Processus(id_ProcessusFile,"Processus_"+filename1,processus.getEmission_date(),Status.EN_COURS,f,null);

        processusService.saveProcessus(processusF);
//
      Processus pr= processusRepository.findProcessusByName("Processus_"+filename1);

        Date d1=new Date(System.currentTimeMillis());
        processusUserRepository.save(new processus_user(user,pr,"true",d1));



        if(nbSign==1)
            x= (int) ((doc.getPages().get(0).getActualSize().getWidth())-30);
        else if(nbSign==2)
        {
            x=280;
            y=120;
//            y=130;

        }
        else if(nbSign==3){
//            x=550;
            x= (int) ((doc.getPages().get(0).getActualSize().getWidth())-30);
        }
        else{
            // set document permissions to prohibit changes
            x=280;
            y=80;
            nbSign=0;
        }
        System.out.println("x:" + x);


        doc.close();


            try {


//                fErr= f1;
//                storageService.store(f1);
                message += "File signed successfully: " + "\\n";


                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not sign the file: " + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }

    }





//    @PostMapping("/sign/{filename}")
//    public ResponseEntity<ResponseMessage> signFile(@PathVariable String filename) throws Exception {
//        String message = "";
//        MultipartFile fErr = null;
//        //System.out.println(new String(file.getBytes()));
//        //storageService.store(file);
//
//        String id=getFileByName(filename);
//        File f=storageService.getFile(id);
//        System.out.println(id);
//        System.out.println(f);
//        MultipartFile f1=new MockMultipartFile("file",f.getName(),f.getType(),f.getData());
//        byte[] d1=f1.getBytes();
//        String dd=new String(d1);
//        encryptionService=new EncryptionService();
//        PrivateKey privateKey=encryptionService.privateKeyFromJKS("aziz.jks","123456","aziz");
//        String data="a9wa aziz fl 3alem";
//        byte[] base64ByteArray = Base64.encode(d1);
//        String signature = encryptionService.rsaSign(base64ByteArray, privateKey);
////            String signedDoc=dd+"_.._"+signature;
//        System.out.println("Signature :"+signature);
//
//        String humanReadableString = new String(base64ByteArray);
//
//        System.out.println("==================");
//        System.out.println("Signature verification");
//        String signedDocReceived=humanReadableString+"_.._bkkLcTJuSTnsEt/GCwfh0efCDYsysyyGYY/+SUJZFnAsMCpsyhH7JKgFp/CNfZFhHZ+ujCSmweXktWdTxyYwBopsjcrEH+Narum5l6zD+M4rzb8HendO7QvkAI5p4pzC03ZjIF/aMoYGVCXlZqOdrIpkG33vXkmnTq2bLbP1BEnneBsLHxhhDvP3HzXQTAu0E1PoAabibzQokibZfcOc1UWxcx8iQfEvDwagP5OVtqoDNfvFCaIfuj7QUDCM0JxoxzCbHDOy5u/PTqR67t7JndsT+vGaL2yjmcMrfiYX/SZjjq1MOILOx5TEkBZ0DnQ/g5fWt3W9BrJ0GS29SD7XXA==";
//        byte[] encryptedData=signedDocReceived.getBytes();
//        MultipartFile finalFinal=new MockMultipartFile("file","signed_"+f1.getOriginalFilename(),f1.getContentType(),encryptedData);
//        storageService.store(finalFinal);
//        //Store encrypted file in processus
//        String id1=storageService.getFiles(finalFinal.getOriginalFilename());
//        File file_processus=storageService.getFile(id1);
//        System.out.println("idFile: "+id1);
//        Long id_Processus=processusService.getProcessusByName("processus_"+f1.getOriginalFilename());
//        System.out.println("idProc: "+id_Processus);
//        Processus processus=processusRepository.getById(id_Processus);
//        processus.setStatus(Status.TERMINÉ);
//        processusRepository.setFinalFileById(file_processus,id_Processus);
////        Processus processus=new Processus(null,"ff",null,file_processus);
////        processusService.saveProcessus(processus);
//
//
//        PublicKey publicKey=encryptionService.publicKeyFromCertificate("myCertificate.cert");
//        System.out.println("==================");
//        boolean b = encryptionService.rsaSignVerify(signedDocReceived, publicKey);
//        System.out.println("==================");
//        System.out.println(b?"Signature ok":"Signature not ok");
//        try {
//
//
////                fErr= f1;
////                storageService.store(f1);
//                message += "File signed successfully: " + f1.getOriginalFilename()+"\\n";
//
//
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
//        } catch (Exception e) {
//            message = "Could not sign the file: " + fErr.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
//        }
//
//    }




    @PostMapping("/uploadd")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("files") MultipartFile[] files) throws Exception {



        String message = "";
        MultipartFile fErr = null;

        if(storageService.getFiles(files[0].getOriginalFilename())!=null){
            message = "fichier déja chargé:  !";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }

        String fileName = StringUtils.cleanPath(files[0].getOriginalFilename());
        File FileDB = new File(fileName, files[0].getContentType(), files[0].getBytes(), 0);



        try {
            for (MultipartFile f:files) {

                    fErr=f;
                    storageService.store(f,0);
                    String id=storageService.getFiles(f.getOriginalFilename());
                    File file=storageService.getFile(id);
                    Date d1=new Date(System.currentTimeMillis());
                    System.out.println(d1);
                    Processus processus=new Processus((Long) null,"Processus_"+f.getOriginalFilename(),new Date(System.currentTimeMillis()), Status.EN_COURS,file,null);
                    this.processusService.saveProcessus(processus);
                    message += "Uploaded the file successfully: " + f.getOriginalFilename()+"\\n";
            }

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + fErr.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }

    }
    @GetMapping("/files")
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(dbFile.getId()))
                    .toUriString();
            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        File fileDB = storageService.getFile(id);
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @GetMapping("/name/{name}")
    public String getFileByName(@PathVariable String name) {
        System.out.println("name: "+name);
        String id = storageService.getFiles(name);
        return id;
    }

    @GetMapping("/namee/{name}")
    public File getFileByNamee(@PathVariable String name) {
        System.out.println("name: "+name);
        String id = storageService.getFiles(name);
        File file =storageService.getFile(id);
        return file;
    }


    //define a method to download files
    @GetMapping(value="/downloadd/{filename}")
    public ResponseEntity<FileSystemResource> downloadFiles(@PathVariable("filename") String filename) throws IOException {

        FileSystemResource resource = new FileSystemResource("c://users//asus//Downloads//"+filename);

        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        // 3
        ContentDisposition disposition = ContentDisposition
                // 3.2
                .attachment() // or .attachment()
                // 3.1
                .filename(resource.getFilename())
                .build();
        headers.setContentDisposition(disposition);


        return new ResponseEntity<>(resource, headers, HttpStatus.OK);



    }

    @GetMapping(value="/view/{filename}")
    public ResponseEntity<FileSystemResource> viewFiles(@PathVariable("filename") String filename) throws IOException {
//        Path filePath=get(DIRECTORY).toAbsolutePath()
//                .normalize()
//                .resolve(filename);
        FileSystemResource resource = new FileSystemResource("c://users//asus//Downloads//"+filename);
        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        // 3
        ContentDisposition disposition = ContentDisposition
                // 3.2
                .inline() // or .attachment()
                // 3.1
                .filename(resource.getFilename())
                .build();
        headers.setContentDisposition(disposition);


        return new ResponseEntity<>(resource, headers, HttpStatus.OK);



    }




}

