package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableAutoConfiguration
@Transactional
@Entity
public class Processus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_processus;

    String name_processus;

    Date emission_date;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Status status;


//    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JoinTable(name="processus_user",
//            joinColumns={@JoinColumn(name = "id_processus")},
//            inverseJoinColumns = {@JoinColumn(name = "id_user")})
//    Set<User> signataires;


    @OneToOne
    @JoinColumn(name="initial_file",referencedColumnName = "id_file")
    File initialFile;

    @OneToOne
    @JoinColumn(name="final_File",referencedColumnName = "id_file")
    File finalFile;
}
