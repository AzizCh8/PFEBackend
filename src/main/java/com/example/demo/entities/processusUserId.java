package com.example.demo.entities;

import java.io.Serializable;

public class processusUserId implements Serializable {
    private Long user;
    private Long processus;


    public processusUserId(){

    }

    public processusUserId(Long id_user, Long id_processus) {
        this.user = id_user;
        this.processus = id_processus;
    }


    public Long getId_user() {
        return user;
    }

    public void setId_user(Long id_user) {
        this.user = id_user;
    }

    public Long getId_processus() {
        return processus;
    }

    public void setId_processus(Long id_processus) {
        this.processus = id_processus;
    }


}
