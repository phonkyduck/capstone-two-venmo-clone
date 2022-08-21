package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {
    List<Transfer> findAll(User currentUser);
//    List<Transfer> findTransferToUser(int id, User currentUserId);
//    List<Transfer> findTransferToUser(String username , User currentUser);
//    List<Transfer> findTransferFromUser(int id, User currentUserId);
//    List<Transfer> findTransferFromUser(String username, User currentUser);
    Transfer findById(int transferId, User currentUser);
    List<Transfer> findTransfer(String username, User currentUser);
    List<Transfer> findTransfer(int id, User currentUser);
    List<Transfer> findTransfer(String username, User currentUser, int isFrom);
    List<Transfer> findTransfer(int id, User currentUser, int isFrom);



}
