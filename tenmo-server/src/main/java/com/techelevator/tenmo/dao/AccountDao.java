package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.UserAccount;

import java.math.BigDecimal;

public interface AccountDao {

    UserAccount getAccount(int id);

    BigDecimal addTE(BigDecimal amount, int id);
    BigDecimal transferTE(BigDecimal amount, int to, int from);
    //BigDecimal requestTE();

}
