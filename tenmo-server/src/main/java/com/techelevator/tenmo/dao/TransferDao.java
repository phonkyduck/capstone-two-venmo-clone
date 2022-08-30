package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    List<Transfer> findAll(User currentUser);

    Transfer findById(int transferId, User currentUser);
    Transfer findById(int transferId);
    List<Transfer> findTransfer(String username, User currentUser);
    List<Transfer> findTransfer(int id, User currentUser);
    List<Transfer> findTransfer(String username, User currentUser, int isFrom);
    List<Transfer> findTransfer(int id, User currentUser, int isFrom);
    void addTransfer(Transfer transfer);
}
