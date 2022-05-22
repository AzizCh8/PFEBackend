package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Transactional
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EnableAutoConfiguration
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;
    private String first_name;
    private String last_name;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role_id;
    @JsonIgnore
    private String password;


    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name="id_dep")
    @JsonIgnore
    private Departement id_dep;

    public Role getRole_id(Role role_id) {
        return this.role_id;
    }

//    @com.fasterxml.jackson.annotation.JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "signataires")
//    private Set<Processus> processuses;
//
//    public Set<Processus> getProcessuses() {
//        return processuses;
//    }
//
//    public void setProcessuses(Set<Processus> processuses) {
//        this.processuses = processuses;
//    }
}
