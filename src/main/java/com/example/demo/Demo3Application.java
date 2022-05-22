package com.example.demo;

import com.example.demo.controller.FileController;
import com.example.demo.dao.DepartementRepository;
import com.example.demo.dao.FileRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.*;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class Demo3Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileController fileController;


    @Autowired
    private RepositoryRestConfiguration restConfiguration;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProcessusService processusService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DepartementService departementService;


    private EncryptionService encryptionService;

    public static void main(String[] args) {
        SpringApplication.run(Demo3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        restConfiguration.exposeIdsFor(User.class,Departement.class);

//       String id=fileStorageService.getFile1("Remarques.docx");
//        File f1=fileStorageService.getFile(id);
//        System.out.println("id:  "+f1.getId());




//        File file=fileStorageService.getFile("0c387b37-4fee-44af-82e5-9b0e5072837e");
//
//        Processus processus=new Processus((Long) null,"aziz", file,null);
//        processusService.saveProcessus(processus);




//        System.out.println("dd\n\n\n\n");
//        System.out.println(new String(dd.getBytes()));
//
//        System.out.println("d1\n\n\n\n");
//        System.out.println(new String(d1));



//        encryptionService=new EncryptionService();
//        PrivateKey privateKey=encryptionService.privateKeyFromJKS("aziz.jks","123456","aziz");
//        String data="a9wa aziz fl 3alem";
//        String signature = encryptionService.rsaSign(d1, privateKey);
//        String signedDoc=dd+"_.._"+signature;
//        System.out.println("Signature :"+signature);
//        System.out.println(signedDoc);
//        System.out.println("==================");
//        System.out.println("Signature verification");
//        String signedDocReceived=dd+"_.._Y9Z8B24TjNyUPQvBLCD5gh3RgnCXWz+v9xYcmaNbErVg+Uqt3CtyiDgfTWrlsqdi3GAtIjKjIz3rcs5HwMbiC2/fIOgB0CzIBzFNHa8Ob0kPy03LXCbMgAE1kN5CNlbAk/yMcGPh9IlleDoD51M4ndMzOiSLBbpDGXAKfOt+oH32ZfMkXuQXlyh9kWzG7RG6/oqaTs2rVEPmt0Y/h+45lOQ1qgps3ItldePqLzd2e6DG2W2zwYITMfx6GPhQT4Zg37bWzVi6OBZyyPfwsxdkng5uS7lUXBHsRtaQTlSZVEi3aUD/6qFz/chgXCjINzJq20GYugZfL7meRAmcEywVlw==";
//        PublicKey publicKey=encryptionService.publicKeyFromCertificate("myCertificate.cert");
//        boolean b = encryptionService.rsaSignVerify(signedDocReceived, publicKey);
//        System.out.println(b?"Signature ok":"Signature not ok");


          userRepository.findAll().forEach(
                u->{System.out.println(u.toString());
                });
      /*Departement finance=new Departement(null,"finance");
      Departement marketing=new Departement(null,"marketing");
       User aziz=new User(null,"chebbahaziz5@gmail.com","aziz","chebbah", Role.ADMIN,"123",finance);
       User ahmed=new User(null,"chebbahahmed@gmail.com","ahmed","chebbah", Role.USER,"123",finance);

       List<User> users= Arrays.asList(aziz,ahmed);
        finance.setUsers(users);

        departementService.saveDepartement(finance);*/



        departementRepository.findAll().forEach(
                u->{System.out.println(u.toString());
                });
//
//        Departement finance=departementRepository.findDepById_dep(1L);
//
//        User aziz=new User(null,"chebbahaziz5@gmail.com","aziz","chebbah", Role.ADMIN,"123",null,null);
//        accountService.saveUser(aziz);
//        userRepository.updateByUsername1("amerzaouei@gail.com",aziz);


    }

    @Bean
    public BCryptPasswordEncoder getBCPE()
    {
        return new BCryptPasswordEncoder();
    }




}
