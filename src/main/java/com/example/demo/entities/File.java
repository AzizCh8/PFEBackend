package com.example.demo.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@EnableAutoConfiguration
@Transactional
@Table(name = "file")
public class File {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id_file;
    private String name_file;
    private String type_file;
    @Lob
    private byte[] data;
    private int nbSignatures;

    public File() {
    }
    public File(String name, String type, byte[] data, int nbSignatures) {
        this.name_file = name;
        this.type_file = type;
        this.data = data;
        this.nbSignatures = nbSignatures;

    }
    public String getId() {
        return id_file;
    }
    public String getName() {
        return name_file;
    }
    public void setName(String name) {
        this.name_file = name;
    }
    public String getType() {
        return type_file;
    }
    public void setType(String type) {
        this.type_file = type;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }

    public int getNbSignatures() {
        return nbSignatures;
    }

    public void setNbSignatures(int nbSignatures) {
        this.nbSignatures = nbSignatures;
    }
}
