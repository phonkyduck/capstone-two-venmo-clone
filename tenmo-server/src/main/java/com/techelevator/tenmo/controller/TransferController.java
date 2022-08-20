package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/user/transfer")
public class TransferController {
    private JdbcTransferDao transferDao;
    private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    private List<Transfer> getAll(Principal user){
        return transferDao.findAll(userDao.findByUsername(user.getName()));
    }




}
