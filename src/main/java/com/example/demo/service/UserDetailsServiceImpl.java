package com.example.demo.service;

import com.example.demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=accountService.findUserByUsername(email);
        if(user==null)
            throw new UsernameNotFoundException(email);
        Collection<GrantedAuthority> authority= new ArrayList<>();
        authority.add(new SimpleGrantedAuthority(String.valueOf(user.getRole_id(user.getRole_id()))));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authority);
    }
}
