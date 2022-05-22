package com.example.demo.controller;

import com.example.demo.dao.UserRepository;
import com.example.demo.entities.User;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;



    @PostMapping(value="/register")
    public User register(@RequestBody User user)
    {
        return accountService.saveUser(user);
    }

    @PostMapping(value="/register1")
    public User stock(@RequestBody User user)
    {
        return accountService.saveUser(user);
    }




    public User find(String username)
    {
        return accountService.findUserByUsername(username);
    }

    @GetMapping("/username")
    public Page<User> find1(@RequestParam String username,
                            @RequestParam int page
                            )
    {
        Page<User> users=userRepository.findByName(username,PageRequest.of(page,5));
        return users;
    }

    @GetMapping("/username1")
    public User findUser(@RequestParam String username)
    {
        return accountService.findUserByUsername(username);
    }


    @GetMapping("/userId")
    public String findUser1(@RequestParam String username)
    {
        return accountService.findByUsername1(username);
    }

    @GetMapping("/userById")
    public User findUserById(@RequestParam Long id)
    {
        return accountService.findUserById(id);
    }

}
