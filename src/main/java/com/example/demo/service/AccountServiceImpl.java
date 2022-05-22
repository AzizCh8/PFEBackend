package com.example.demo.service;

import com.example.demo.dao.UserRepository;
import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;
    String hpw;
    public User saveUser(User user){
        if(user.getPassword().length()<=12) {
            hpw = bCryptPasswordEncoder.encode(user.getPassword());
        }
        else
            hpw=user.getPassword();
        user.setPassword(hpw);
        return userRepository.save(user);
    }




    public User findUserByUsername(String email)
    {
        return userRepository.findByUsername(email);
    }

    public String findByUsername1(String name){
        return this.userRepository.findByUsername1(name);
    }

    public User findUserById(Long id){return this.userRepository.findUserById(id);}

}
