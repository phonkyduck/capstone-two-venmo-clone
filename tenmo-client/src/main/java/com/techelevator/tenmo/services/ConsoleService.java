package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    //Variables
    private final Scanner scanner = new Scanner(System.in);

    //Menu Printing Methods
    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printPendingMenu(){
        System.out.println();
        System.out.println("1: View all pending requests.");
        System.out.println("2: View my pending requests.");
        System.out.println("3: View requests awaiting my approval.");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printTransferMenu() {
        System.out.println();
        System.out.println("1: View all transfers");
        System.out.println("2: Search transfer by User ID");
        System.out.println("3: Search transfer by Username");
        System.out.println("4: Search transfer by Transfer ID");
        System.out.println("5: Display all users");
        System.out.println("0. Exit");
        System.out.println();
    }

    public void printTransferFilterMenu() {
        System.out.println();
        System.out.println("1: Filter search by recipient");
        System.out.println("2: Filter search by sender");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printPendingApprovalMenu(){
        System.out.println();
        System.out.println("1: Deny pending request");
        System.out.println("2: Approve pending request");
        System.out.println("0: Exit");
        System.out.println();
    }


    //Prompt for User Input Methods

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    //Other Methods

    public boolean havePending(Transfer[] transfers, User user) {
        List<Transfer> pendingCheck = new ArrayList<>();
        for (Transfer t :
                transfers) {
            if (t.getFromUserId() == user.getId()) {
                pendingCheck.add(t);
            }
        }
        if (pendingCheck.size()>0){
            return true;
        } else {
            return false;
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
