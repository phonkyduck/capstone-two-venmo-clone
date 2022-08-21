package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/user/transfer")
public class TransferController {
    private JdbcTransferDao transferDao;
    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    @GetMapping
    private List<Transfer> getAllFromUser(Principal user){
        return transferDao.findAll(userDao.findByUsername(user.getName()));
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

    @GetMapping
    private Transfer getById(int transferId, Principal user){
        return transferDao.findById(transferId, userDao.findByUsername(user.getName()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    private List<Transfer> getAllAdmin(){
        return transferDao.findAllAdmin();
    }
}
