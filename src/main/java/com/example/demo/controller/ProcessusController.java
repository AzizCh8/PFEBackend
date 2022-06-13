package com.example.demo.controller;


import com.example.demo.dao.ProcessusRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.entities.Processus;
import com.example.demo.entities.User;
import com.example.demo.service.ProcessusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processus")
public class ProcessusController {

    @Autowired
    private ProcessusService processusService;

    @Autowired
    private ProcessusRepository processusRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/register")
    public Processus saveProcessus(@RequestBody Processus processus){
        return processusService.saveProcessus(processus);
    }

    @GetMapping("/processus")
    public Page<Processus> find1(@RequestParam String name_processus,
                            @RequestParam int page
    )
    {
        Page<Processus> processus=processusRepository.findByName(name_processus, PageRequest.of(page,5));
        return processus;
    }


    @GetMapping("/usersByProcessusId")
    public List<User> getUsersByProcessusId(@RequestParam("idP") Long idP)
    {
           List<User> users=userRepository.findUsersByProcessusId(idP);
        return users;
    }


//
//    @GetMapping("/processByUser")
//    List<Processus> findProcessusByUser(@RequestParam(value="id1") Long id){
//        return processusService.findProcessusByUser(id);
//    }
}
