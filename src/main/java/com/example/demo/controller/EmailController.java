package com.example.demo.controller;


import com.example.demo.dao.ProcessusRepository;
import com.example.demo.dao.ProcessusUserRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Email;
import com.example.demo.entities.Processus;
import com.example.demo.entities.User;
import com.example.demo.entities.processus_user;
import com.example.demo.service.EmailService;
import com.example.demo.service.ProcessusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private ProcessusService processusService;

    @Autowired
    private ProcessusRepository processusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProcessusUserRepository processusUserRepository;

    private Email email=new Email();

    //some other code


    @PostMapping(value = "/email")
    public ResponseEntity<Email> enviarEmail(@RequestBody Email email){
        try {
            emailService.sendEmail(email.getTo(),email.getObject(),email.getMessage());
            return new ResponseEntity<>(email,  HttpStatus.OK);
        } catch( MailException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/emailSign")
    public ResponseEntity<Email> enviarEmail1(@RequestBody Email email){
        try {
//            User user=userRepository.findByName1(email.getTo());
//            Long id_Processus=processusService.getProcessusByName("processus_"+email.getAttachment());
//            System.out.println("idProc: "+id_Processus);
//            Processus processus=processusRepository.getById(id_Processus);
//            processusRepository.setSigantaireById(user,id_Processus);

            User user=userRepository.findByName1(email.getTo());
            System.out.println("user: "+user.getFirst_name());
            Long id_Processus=processusService.getProcessusByName("processus_"+email.getAttachment());
            System.out.println("idProc: "+id_Processus);
            Processus processus=processusRepository.getById(id_Processus);
//            processus.getSignataires().add(user);
//            processusService.saveProcessus(processus);
//            Long idPr=processusRepository.findByName_processus("processus_"+email.getAttachment());
//            System.out.println(processus.getSignataires().toArray().length);
            processusUserRepository.save(new processus_user(user,processus,"false"));
//            processusRepository.updateprocessus_userFalse(email.getTo(),idPr);
            emailService.sendEmail(email.getTo(),email.getObject(),email.getMessage());
            return new ResponseEntity<>(email,  HttpStatus.OK);
        } catch( MailException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PostMapping(value = "/att")
    public ResponseEntity<Email> enviarEmailAtt(@RequestBody Email email){
        try {
            emailService.sendEmailAttachement(email.getTo(),email.getObject(),email.getMessage(),email.getAttachment());

//             User user=userRepository.findByName1(email.getTo());
//            Long id_Processus=processusService.getProcessusByName("processus_"+email.getAttachment());
//            System.out.println("idProc: "+id_Processus);
//            Processus processus=processusRepository.getById(id_Processus);
//            processus.getSignataires().add(user);
            return new ResponseEntity<>(email,  HttpStatus.OK);
        } catch( MailException e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



}


