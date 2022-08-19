package com.techelevator.tenmo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class UserAccount {

    @NotBlank
    private int accountId;
    @NotEmpty
    private int userId;
    @NotEmpty
    private BigDecimal balance = new BigDecimal(1000).setScale(2,RoundingMode.FLOOR);

    public UserAccount(int userId){
        this.userId = userId;
    }

    public UserAccount(int accountId, int userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance.setScale(2, RoundingMode.FLOOR);
    }

    public UserAccount() {

    }

    public int getUser() {
        return userId;
    }

    public void setUser(int user) {
        this.userId = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

}
