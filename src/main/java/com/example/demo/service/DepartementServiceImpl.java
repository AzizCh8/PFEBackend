package com.example.demo.service;

import com.example.demo.dao.DepartementRepository;
import com.example.demo.entities.Departement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class DepartementServiceImpl implements DepartementService{
    @Autowired
    DepartementRepository departementRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    public Departement saveDepartement(Departement departement)
    {
//        departement.getUsers().forEach(user -> {
//            String hpw=bCryptPasswordEncoder.encode(user.getPassword());
//            user.setPassword(hpw);
//        });
        return departementRepository.save(departement);

    }

    public String getNameDep(Long id){
        return departementRepository.findById_dep(id);
    }
}
