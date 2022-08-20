package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> findAll(int currentUserId);
    List<Transfer> findTransferToUser(int id, int currentUserId);
    List<Transfer> findTransferToUser(String username , int currentUserId);
    List<Transfer> findTransferFromUser(int id, int currentUserId);
    List<Transfer> findTransferFromUser(String username,int currentUserId);
    Transfer findById(int transferId, int currentId);

}
