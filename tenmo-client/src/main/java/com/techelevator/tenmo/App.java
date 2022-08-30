package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.*;
import io.cucumber.java.bs.I;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private AuthenticatedUser currentUser;

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private AccountService accountService;
    private TransferService transferService;
    private UserService userService;
    private final SelectionService selectionService = new SelectionService();
    private final User user = new User();

    public void startTransferService(){
        this.transferService = new TransferService(currentUser.getToken(),accountService);
    }

    public void startAccountService(){
        this.accountService = new AccountService(currentUser.getToken(), currentUser.getUser());
    }

    public void startUserService(){
        this.userService = new UserService(currentUser.getToken(), currentUser.getUser());
    }
    public void startServices(){
        startUserService();
        startAccountService();
        startTransferService();
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
            startUserService();
            startAccountService();
            startTransferService();
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
        accountService.printCurrentBalance();
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
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
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printPendingMenu();
            Transfer[] transfers = transferService.getPendingRequest();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                selectionService.printAllRequest(transfers, currentUser.getUser());
            } else if (menuSelection == 2) {
                selectionService.printMyPending(transfers, currentUser.getUser());
            } else if (menuSelection == 3) {
                if (consoleService.havePending(transfers, currentUser.getUser())) {
                    selectionService.printPendingApproval(transfers, currentUser.getUser());
                    pendingApprovalSubMenu();
                } else {
                    System.out.println("No Pending Request");
                    continue;
                }
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
                menuSelection = -1;
            }
            consoleService.pause();
        }
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        System.out.println("Please select a user to send money to:");
        selectionService.printArray(userService.getUsers());
        User recipient;
        String toUser = consoleService.promptForString("Please enter the recipient's username: ");
        recipient = userService.findUserByString(toUser);
        BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount you'd like to send: ");
        Transfer transfer = transferService.prepareTransfer(recipient, currentUser.getUser(), amount, 2, 2);
        String error = transferService.sendTE(transfer);
        accountService.printSendCheck(error);

	}

	private void requestBucks() {
		// TODO Auto-generated method stub
        System.out.println("Please select a user to request money from:");
        selectionService.printArray(userService.getUsers());
        User requestee;
        String fromUser = "";
        boolean isString = true;
        while (isString) {
            System.out.println("Who are you requesting money from?");
            fromUser = consoleService.promptForString("Please enter the username: ");
            try {
                requestee = userService.findUserByString(fromUser);
                BigDecimal amount = consoleService.promptForBigDecimal("Please enter the amount you'd like to request: ");
                Transfer transfer = transferService.prepareTransfer(currentUser.getUser(), requestee, amount, 1, 1);
                String error = transferService.requestTE(transfer);
                accountService.printRequestCheck(error);
                isString = false;
            }catch (Exception e){
                System.out.println("Invalid Username");
            }finally {
                continue;
            }


            }
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

    private void pendingApprovalSubMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printPendingApprovalMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {

                    int requestTransferId = consoleService.promptForInt("Please enter the request transfer Id: ");
                    try {
                        System.out.println(transferService.denyTransfer(transferService.getTransferById(requestTransferId)));
                    } catch (Exception e){
                        System.out.println("Transfer is Does not Exist or is Invalid  ");
                        continue;
                    }


                continue;
            } else if (menuSelection == 2) {

                //change transfer_status_id to 2, and execute the send
                boolean error = false;
                    try {
                        int requestTransferId = consoleService.promptForInt("Please enter the request transfer Id: ");
                         error = transferService.approveTransfer(transferService.getTransferById(requestTransferId));

                    } catch (Exception e) {
                        System.out.println("Transfer is Does not Exist or is Invalid  ");
                        continue;
                    }

                if (error) {
                    viewCurrentBalance();
                }
                continue;
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
        }
    }

    private void findTransferByID() {
        int transferId = consoleService.promptForInt("Please enter the Transfer ID: ");
        selectionService.printTransferDetails(transferService.getTransferById(transferId));
    }

}
