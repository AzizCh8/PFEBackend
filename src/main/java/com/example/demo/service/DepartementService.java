package com.example.demo.service;

import com.example.demo.entities.Departement;

public interface DepartementService {
    public Departement saveDepartement(Departement departement);
    public String getNameDep(Long id);
}
