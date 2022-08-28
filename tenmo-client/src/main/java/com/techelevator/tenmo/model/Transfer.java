package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int id;
    private int type;
    private int status;
//    private int toId;
//    private String toUser;
//    private int fromId;
//    private String fromUser;
    private User toUser;
    private User fromUser;
    private BigDecimal amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    public int getToId() {
//        return toId;
//    }
//
//    public void setToId(int toId) {
//        this.toId = toId;
//    }
//
//    public int getFromId() {
//        return fromId;
//    }
//
//    public void setFromId(int fromId) {
//        this.fromId = fromId;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

//    public String getToUser() {
//        return toUser;
//    }
//
//    public void setToUser(String toUser) {
//        this.toUser = toUser;
//    }
//
//    public String getFromUser() {
//        return fromUser;
//    }

//    public void setFromUser(String fromUser) {
//        this.fromUser = fromUser;
//    }
}
