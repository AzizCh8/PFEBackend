package com.example.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EnableAutoConfiguration
@Transactional
@IdClass(processusUserId.class)
@Entity
public class processus_user {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_processus", referencedColumnName = "id_processus")
    private Processus processus;

    @Column(name = "signed")
    private String signed;


    @Column(name = "signature_date")
    private Date signature_date;


    public processus_user(User user, Processus processus,String signed,Date signature_date) {
        this.user = user;
        this.processus = processus;
        this.signed = signed;
        this.signature_date=signature_date;
    }
}
