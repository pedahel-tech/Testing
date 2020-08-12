package com.fm.authentication.controller;

import com.fm.authentication.entities.User;
import com.fm.authentication.respositories.RoleRepository;
import com.fm.authentication.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.integration.support.MessageBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by sadiq.odho on 9/11/2018.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    /* Maps to all HTTP actions by default (GET,POST,..)*/
    @RequestMapping("/all")
    public @ResponseBody
    List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/get-logged-in-user")
    public User getLoggedInUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username != null){
            return userRepository.findByEmail(username);
        }
        return null;
    }

    @PostMapping("/register")
    public User singUp(@RequestBody User user) throws Exception{
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("There is an account with that email adress: " + user.getEmail());
        }

        User newUser = new User();
        newUser.setName(user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());

        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }
}