package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(path = "/user/transfer")
public class TransferController {

    @Autowired
    private JdbcTransferDao transferDao;
    @Autowired
    private JdbcUserDao userDao;
    @Autowired
    private JdbcAccountDao accountDao;

//    public TransferController(JdbcTransferDao transferDao, JdbcUserDao userDao, JdbcAccountDao accountDao) {
//        this.transferDao = transferDao;
//        this.userDao = userDao;
//        this.accountDao = accountDao;
//    }

    @GetMapping(path = "/getAll")
    private List<Transfer> getAllFromUser(Principal user){
        return transferDao.findAll(/*userDao.findByUsername(user.getName())*/);
    }

    @GetMapping
    private List<Transfer> getTransfer(@RequestParam(value = "username_like", defaultValue = "") String username, Principal user, @RequestParam(value = "filter", defaultValue = "0") int isFrom, @RequestParam(value = "user_id", defaultValue = "0") int id){
        if(!username.isEmpty() && isFrom == 0){
            return transferDao.findTransfer(username, userDao.findByUsername(user.getName()));
        } else if(username.isEmpty() && isFrom == 0) {
            return transferDao.findTransfer(id, userDao.findByUsername(user.getName()));
        } else if(!username.isEmpty() && isFrom == 1 || !username.isEmpty() && isFrom == -1) {
            return transferDao.findTransfer(username, userDao.findByUsername(user.getName()), isFrom);
        } else if(username.isEmpty() && isFrom == 1 || username.isEmpty() && isFrom == -1) {
            return transferDao.findTransfer(id, userDao.findByUsername(user.getName()), isFrom);
        } else {
            return null;
        }
    }

    @GetMapping(path = "/{id}")
    private Transfer getById(@PathVariable int transferId, Principal user){
        return transferDao.findById(transferId, userDao.findByUsername(user.getName()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/{id}")
    private Transfer getById(@PathVariable int transferId){
        return transferDao.findById(transferId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/adminAll")
    private List<Transfer> getAllAdmin(){
        return transferDao.findAllAdmin();
    }
}
