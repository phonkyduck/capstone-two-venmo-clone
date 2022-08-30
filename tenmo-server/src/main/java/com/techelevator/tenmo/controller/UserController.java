package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller to manage users */

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private JdbcUserDao userDao;

    @GetMapping
    public List<User> getAll(){
        return userDao.findAll();
    }

    @PostMapping
    public boolean createUser(@RequestBody String username, @RequestBody String password){
        return userDao.create(username, password);
    }
}
