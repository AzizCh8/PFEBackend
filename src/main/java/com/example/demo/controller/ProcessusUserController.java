package com.example.demo.controller;


import com.example.demo.dao.ProcessusUserRepository;
import com.example.demo.entities.User;
import com.example.demo.entities.processus_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/processusUser")
public class ProcessusUserController {

    @Autowired
    private ProcessusUserRepository processusUserRepository;

    @GetMapping("/processusUserList")
    public List<processus_user> getUsersByProcessusId(@Param("idP") Long idP)
    {
        List<processus_user> liste=processusUserRepository.findByProcessusId(idP);
        return liste;
    }
}
