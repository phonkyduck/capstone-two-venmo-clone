package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.security.Principal;

/** Controller to manage user accounts*/

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "/user/account/")
public class AccountController {

    @Autowired
    private JdbcUserDao userDao;
    @Autowired
    private JdbcAccountDao accountDao;

    @GetMapping
    public BigDecimal getBalance(Principal user ){
        Account account = accountDao.getAccount(userDao.findIdByUsername(user.getName()));
        return account.getBalance();
    }

    @PutMapping(path = "/add")
    public BigDecimal addToAccount(@RequestBody BigDecimal amount, Principal user){
        accountDao.addTE(amount, userDao.findIdByUsername( user.getName()));
        return amount;
    }
}
