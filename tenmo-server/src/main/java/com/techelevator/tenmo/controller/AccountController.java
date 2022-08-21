package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping(path = "user/account/")
public class AccountController {
private JdbcUserDao userDao;
    private JdbcAccountDao accountDao;

    @GetMapping
    public BigDecimal getBalance(Principal user ){
        Account account = accountDao.getAccount(userDao.findIdByUsername( user.getName()));
        return account.getBalance();
    }

    @PutMapping(path = "/{to}-{from}")
    public BigDecimal transfer(@RequestBody BigDecimal amount, @RequestParam int to, @RequestParam int from){
        accountDao.transferTE(amount, to, from);
        return amount;
    }

    @PutMapping(path = "/add")
    public BigDecimal addToAccount(@RequestBody BigDecimal amount, Principal user){
        accountDao.addTE(amount, userDao.findIdByUsername( user.getName()));
        return amount;
    }

}
