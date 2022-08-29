package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

public class SelectionService {

    public void printArray(User[] users){
        int count = 1;
        for(int i = 0; i < users.length; i++){
            System.out.println(count + ": " + users[i].getUsername());
            count++;
        }
    }

    public void printArray(Transfer[] transfers){
        for(int i = 0; i < transfers.length; i++){
            System.out.println("Transfer ID: " + transfers[i].getId() + " " + transfers[i].getFromUser().getUsername() + " sent " +
                    transfers[i].getToUser().getUsername() + " $" + transfers[i].getAmount() + ".");
        }
    }

    public void printTransfer(Transfer transfers){
        if (transfers == null){
            System.out.println("No transfer found.");
        } else {
            System.out.println("Transfer ID: " + transfers.getId() + " " + transfers.getFromUser().getUsername() + " sent " +
                    transfers.getToUser().getUsername() + " $" + transfers.getAmount() + ".");
        }
    }

}
