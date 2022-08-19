package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserAccount;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "user/account/")
public class AccountController {
    private User user;

    public AccountController(User user) {
        this.user = user;
    }
    public BigDecimal getBalance(@RequestBody User user ){
        JdbcAccountDao.getAccount(user.getId());
    }
}
