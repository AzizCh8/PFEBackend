package com.example.demo.service;

import com.example.demo.entities.User;

public interface AccountService {
    public User saveUser(User user);
    public User findUserByUsername(String username);
    public String findByUsername1(String name);
    public User findUserById(Long id);
}
