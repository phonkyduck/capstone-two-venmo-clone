package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/user/transfer")
public class TestController {
    @Autowired
    private JdbcTransferDao transferDao;

    @Autowired
    private JdbcUserDao userDao;

    @GetMapping(path = "/getAll")
    private List<Transfer> getAll(Principal user){
        return transferDao.findAll(userDao.findByUsername(user.getName()));
    }

    @GetMapping
    private List<Transfer> getTransfer(@RequestParam(value = "username_like", defaultValue = "") String username, Principal user, @RequestParam(value = "filter", defaultValue = "0") int isFrom, @RequestParam(value = "user_id", defaultValue = "0")int id ){
        if(!username.isEmpty() && isFrom == 0){
            return transferDao.findTransfer(username, userDao.findByUsername(user.getName()));
        } else if (username.isEmpty() && isFrom == 0) {
            return transferDao.findTransfer(id,userDao.findByUsername(user.getName()));
        } else if (!username.isEmpty() && isFrom == 1 || !username.isEmpty() && isFrom == -1) {
            return transferDao.findTransfer(username, userDao.findByUsername(user.getName()), isFrom);
        } else if (username.isEmpty() && isFrom == 1 || username.isEmpty() && isFrom ==-1) {
            return transferDao.findTransfer(id,userDao.findByUsername(user.getName()),isFrom);
        } else {
            return null;
        }
    }

    @GetMapping(path = "/admin/{id}")
    private Transfer getByIdAdmin(@PathVariable int id){
        return transferDao.findById(id);
    }

    @GetMapping(path = "/{id}")
    private Transfer getById(@PathVariable int id){
        return transferDao.findById(id);
    }

    @GetMapping(path = "/adminAll")
    private List<Transfer> getAllAdmin(){
        return transferDao.findAllAdmin();
    }

}
