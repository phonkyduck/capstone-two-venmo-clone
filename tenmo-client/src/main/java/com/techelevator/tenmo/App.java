package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private AuthenticatedUser currentUser;

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService;
    private TransferService transferService;
    private final UserService userService = new UserService();
    private final SelectionService selectionService = new SelectionService();
    private final User user = new User();

    public void startTransferService(){
        this.transferService = new TransferService(currentUser.getToken(), currentUser.getUser());
    }

    public void startAccountService(){
        this.accountService = new AccountService(currentUser.getToken(), currentUser.getUser());
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
        startAccountService();
        accountService.printCurrentBalance();
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
        startTransferService();
		int menuSelection = -1;
		while (menuSelection != 0) {
		    consoleService.printTransferMenu();
		    menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
		    if (menuSelection == 1) {
		        selectionService.printArray(transferService.getAllTransfers());
            } else if (menuSelection == 2) {
		        transferSubMenuId();
            } else if (menuSelection == 3) {
                transferSubMenuUsername();
            } else if (menuSelection == 4)  {
		        findTransferByID();
            } else if (menuSelection == 5) {
		        selectionService.printArray(userService.getUsers());
            } else if (menuSelection == 0) {
		        continue;
            } else {
                System.out.println("Invalid Selection");
                menuSelection = -1;
            }
		    consoleService.pause();
        }
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        System.out.println("Please select a user to send money to:");
        selectionService.printArray(userService.getUsers());
        User recipient;
        String toUser = consoleService.promptForString("Please enter the recipient's username: ");
        recipient = userService.findUserByString(toUser);
        BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount you'd like to send: ");
        String error = accountService.sendTE(currentUser.getUser(), recipient, amount);
        accountService.printSendCheck(error);

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

	private void transferSubMenuId() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printTransferFilterMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                int userId = consoleService.promptForInt("Please enter the User ID of the recipient: ");
                selectionService.printArray(transferService.getTransfer(userId, 1));
            } else if (menuSelection == 2) {
                int userId = consoleService.promptForInt("Please enter the User ID of the sender: ");
                selectionService.printArray(transferService.getTransfer(userId, 0));
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
                menuSelection = 0;
            }
        }
    }

    private void transferSubMenuUsername() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printTransferFilterMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                String username = consoleService.promptForString("Please enter the username of the recipient: ");
                selectionService.printArray(transferService.getTransfer(username, 1));
            } else if (menuSelection == 2) {
                String username = consoleService.promptForString("Please enter the username of the sender: ");
                selectionService.printArray(transferService.getTransfer(username, 0));
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
        }
    }

    private void findTransferByID() {
        int transferId = consoleService.promptForInt("Please enter the Transfer ID: ");
        selectionService.printTransfer(transferService.getTransferById(transferId));
    }

}
