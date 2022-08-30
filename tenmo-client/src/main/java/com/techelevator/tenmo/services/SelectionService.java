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
            System.out.println("Transfer ID " + transfers[i].getId() + ": " + transfers[i].getFromUser().getUsername() + " sent " +
                    transfers[i].getToUser().getUsername() + " $" + transfers[i].getAmount() + ".");
        }
    }

    public void printTransfer(Transfer transfers){
        if (transfers == null){
            System.out.println("No transfer found.");
        } else {
            System.out.println("Transfer ID " + transfers.getId() + ": " + transfers.getFromUser().getUsername() + " sent " +
                    transfers.getToUser().getUsername() + " $" + transfers.getAmount() + ".");
        }
    }

    public void printMyPending(Transfer[] requests, User currentUser){
        for(int i = 0; i < requests.length; i++){
            if (requests[i].getToUserId() == currentUser.getId()){
                System.out.println("Pending Request ID " + requests[i].getId() + ": " + requests[i].getToUser().getUsername() + " requested " +
                        " $" + requests[i].getAmount() + " from " + requests[i].getFromUser().getUsername() + ".");
            }

        }
    }

    public void printPendingApproval(Transfer[] requests, User currentUser){
        for(int i = 0; i < requests.length; i++){
            if (requests[i].getFromUserId() == currentUser.getId()&& requests[i].getStatus()==1){
                System.out.println("Pending Request ID " + requests[i].getId() + ": " + requests[i].getToUser().getUsername() + " requested " +
                        " $" + requests[i].getAmount() + " from " + requests[i].getFromUser().getUsername() + ".");
            }

        }
    }

    public void printAllRequest(Transfer[] requests, User currentUser){
        for(int i = 0; i < requests.length; i++){
            if (requests[i].getFromUserId() == currentUser.getId() || requests[i].getToUserId() == currentUser.getId() && requests[i].getType()==2){
                System.out.println("Pending Request ID " + requests[i].getId() + ": " + requests[i].getToUser().getUsername() + " requested " +
                        " $" + requests[i].getAmount() + " from " + requests[i].getFromUser().getUsername() + ".");
            }

        }
    }
    public void printTransferDetails(Transfer transfers){
        if (transfers == null){
            System.out.println("No transfer found.");
        } else {
            System.out.println("Transfer ID: " + transfers.getId());
            System.out.println("Sender: " + transfers.getFromUser().getUsername());
            System.out.println("Recipient: " + transfers.getToUser().getUsername());
            if(transfers.getType() == 1){
                System.out.println("Type: Request");
            } else if(transfers.getType() == 2){
                System.out.println("Type: Send");
            }
            System.out.println("Amount: " + transfers.getAmount());
            System.out.print("Transfer Status: ");
            if(transfers.getStatus() == 1){
                System.out.print("Pending");
            } else if(transfers.getStatus() == 2){
                System.out.print("Approved");
            } else if(transfers.getStatus() == 3){
                System.out.print("Rejected");
            }
        }
    }
}
