package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserAccount;

import java.math.BigDecimal;

public interface AccountDao {
    UserAccount getAccount();
    BigDecimal addTE();
    BigDecimal transferTE();
    //BigDecimal requestTE();

}
