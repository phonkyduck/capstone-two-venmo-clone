package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "user/account/")
public class AccountController {

    private JdbcAccountDao accountDao;

    @GetMapping
    public BigDecimal getBalance(@RequestBody int id ){
        Account account = accountDao.getAccount(id);
        return account.getBalance();
    }

    @PutMapping(path = "/{to}-{from}")
    public BigDecimal transfer(@RequestBody BigDecimal amount, @RequestParam int to, @RequestParam int from){
        accountDao.transferTE(amount, to, from);
        return amount;
    }

    @PutMapping(path = "/add")
    public BigDecimal addToAccount(@RequestBody BigDecimal amount, @RequestParam int id){
        accountDao.addTE(amount, id);
        return amount;
    }

}
