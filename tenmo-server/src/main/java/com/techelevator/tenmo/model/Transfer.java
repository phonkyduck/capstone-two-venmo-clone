package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int id;
    private String type;
    private String status;
//    private int toId;
    private User toUser;
//    private int fromId;
    private User fromUser;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public int getToId() {
//        return toId;
//    }

//    public void setToId(int toId) {
//        this.toId = toId;
//    }

//    public int getFromId() {
//        return fromId;
//    }

//    public void setFromId(int fromId) {
//        this.fromId = fromId;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }
}
