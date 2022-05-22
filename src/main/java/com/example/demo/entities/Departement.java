package com.example.demo.entities;

import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EnableAutoConfiguration
@Entity
public class Departement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_dep;

    private String name;



    public Departement(String name)
    {
        this.name=name;
    }

//    public Departement(Long id,String name)
//    {   this.id_dep=id;
//        this.name=name;
//    }

}
