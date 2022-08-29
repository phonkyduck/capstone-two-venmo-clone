package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface AccountDao {

    Account getAccount(int id);

    BigDecimal addTE(BigDecimal amount, int id);
    BigDecimal sendTE(Transfer transfer);
    void requestTE(Transfer transfer);
    //BigDecimal requestTE();

}
