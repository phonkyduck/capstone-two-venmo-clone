package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;

public class SelectionService {

    public void printArray(User[] users){
        for(int i = 0; i < users.length; i++){
            int count = 1;
            System.out.println(count + ": " + users[i].getUsername());
            count++;
        }
    }

}
