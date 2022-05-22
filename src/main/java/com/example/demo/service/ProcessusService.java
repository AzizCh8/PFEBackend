package com.example.demo.service;

import com.example.demo.dao.FileRepository;
import com.example.demo.dao.ProcessusRepository;
import com.example.demo.entities.File;
import com.example.demo.entities.Processus;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessusService {

    @Autowired
    public ProcessusRepository processusRepository;

    @Autowired
    public FileRepository fileRepository;

    @Autowired
    AccountService accountService;



    public Processus saveProcessus(Processus processus)
    {

        return this.processusRepository.save(processus);
    }

    //    public Processus saveProcessus1(Processus processus,String email)
    //    {   User user=accountService.findUserByUsername(email);
    //
    //        return this.processusRepository.save(processus);
    //    }

    public Long getProcessusByName(String name)
    {
        return processusRepository.findByName_processus(name);
    }



//    public List<Processus> findProcessusByUser( Long id){
//        return processusRepository.findProcessusByUser(id);
//    }

}
