package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import io.cucumber.core.gherkin.vintage.internal.gherkin.Token;
import org.apiguardian.api.API;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferService {
    public static final String API_BASE_URL = "http://localhost:8080/user/transfer";
    private final RestTemplate restTemplate = new RestTemplate();
    private final AuthenticatedUser authenticatedUser = new AuthenticatedUser();
    private String token;
    private User user;


    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public TransferService(String token, User user, AccountService accountService) {
        this.token = token;
        this.user = user;
        this.accountService = accountService;
    }
    private AccountService accountService;


    public Transfer[] getAllTransfers() {
        Transfer[] myTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response =
            restTemplate.exchange(API_BASE_URL + "/getAll", HttpMethod.GET , makeEntity(), Transfer[].class);
            myTransfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public Transfer getTransferById(int id) {
        Transfer myTransfer = null;
        try {
            ResponseEntity<Transfer> response =
            restTemplate.exchange(API_BASE_URL + "/" + id, HttpMethod.GET, makeEntity(), Transfer.class);
            myTransfer = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfer;
    }

    public Transfer getTransferByIdAdmin(int id) {
        Transfer myTransfer = new Transfer();
        try {
            ResponseEntity<Transfer> response =
            restTemplate.exchange(API_BASE_URL + "/admin/" + id, HttpMethod.GET, makeEntity(), Transfer.class);
            myTransfer = response.getBody();

        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfer;
    }

    public Transfer[] getTransfer(String username) {
        Transfer[] myTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response =
            restTemplate.exchange(API_BASE_URL + "?username_like=" + username, HttpMethod.GET, makeEntity(), Transfer[].class);
            myTransfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public Transfer[] getTransfer(int id) {
        Transfer[] myTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response =
            restTemplate.exchange(API_BASE_URL + "?user_id=" + id, HttpMethod.GET, makeEntity(), Transfer[].class);
            myTransfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public Transfer[] getTransfer(String username, int isFrom) {
        Transfer[] myTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response =
                    restTemplate.exchange(API_BASE_URL + "?username_like=" + username + "&filter=" + isFrom, HttpMethod.GET, makeEntity(), Transfer[].class);
            myTransfers = response.getBody();
            for (Transfer t :
                    myTransfers) {
                t.setUsers();
            }
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public Transfer[] getTransfer(int id, int isFrom) {
        Transfer[] myTransfers = new Transfer[]{};
        try {
            ResponseEntity<Transfer[]> response =
            restTemplate.exchange(API_BASE_URL + "?user_id=" + id + "&filter=" + isFrom, HttpMethod.GET, makeEntity(), Transfer[].class);
            myTransfers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }
    public String sendTE(Transfer transfer){
       BigDecimal accountBalance = accountService.getBalance();
        String error = "";
        int fromUser = Math.toIntExact(transfer.getFromUser().getId());
        int toUser = Math.toIntExact(transfer.getToUser().getId());
        BigDecimal amount = transfer.getAmount();
        try {
            if (amount.compareTo(BigDecimal.valueOf(0)) <= 0){
                error = "zero";
            } else if (fromUser != toUser && accountBalance.compareTo(amount) >= 0) {
                restTemplate.put(API_BASE_URL+"/send", makeTransferEntity(transfer));
                error = "success";
            } else if (fromUser != toUser && accountBalance.compareTo(amount) < 0){
                error = "amount";
            } else if (fromUser == toUser){
                error = "self";
            } else { error = "unknown";}
        } catch(RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return error;
    }
    public Transfer prepareSendTransfer(User toUser, User fromUser, BigDecimal amount){
        Transfer transfer = new Transfer();
        transfer.setToUser(toUser);
        transfer.setFromUser(fromUser);
        transfer.setAmount(amount);
        transfer.setType(2);
        transfer.setStatus(2);
        return transfer;
    }
    public HttpEntity<Void> makeEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }
    public HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(transfer , headers);
    }
}
