package com.example.demo.controller;

import com.example.demo.entities.Processus;
import com.example.demo.service.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @GetMapping("/depName/{id}")
    public String getDepById(@PathVariable Long id)
    {
        return departementService.getNameDep(id);
    }
}
