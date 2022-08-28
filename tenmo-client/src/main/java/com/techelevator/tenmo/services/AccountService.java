package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private BigDecimal accountBalance;

    public static final  String API_BASE_URL = "http://localhost:8080/user/account";
    private final RestTemplate restTemplate = new RestTemplate();

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    private String token;
    private User user;

    public AccountService(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public BigDecimal getBalance(){
        ResponseEntity <BigDecimal> response =
        restTemplate.exchange(API_BASE_URL + "/", HttpMethod.GET, makeEntity(), BigDecimal.class);
        accountBalance = response.getBody();
        return accountBalance;
    }

//    public String sendTE(Transfer transfer){
//        getBalance();
//        String error = "";
//        int fromUser = Math.toIntExact(transfer.getFromUser().getId());
//        int toUser = Math.toIntExact(transfer.getToUser().getId());
//        BigDecimal amount = transfer.getAmount();
//        try {
//            if (amount.compareTo(BigDecimal.valueOf(0)) <= 0){
//                error = "zero";
//            } else if (fromUser != toUser && accountBalance.compareTo(amount) >= 0) {
//                restTemplate.put(API_BASE_URL, makeEntity());
//                error = "success";
//            } else if (fromUser != toUser && accountBalance.compareTo(amount) < 0){
//                error = "amount";
//            } else if (fromUser == toUser && accountBalance.compareTo(amount) >= 0){
//                error = "self";
//            } else { error = "unknown";}
//        } catch(RestClientResponseException e) {
//            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
//        } catch (ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return error;
//    }

    private HttpEntity<User> makeEntity() {
        User user = getUser();
        String token = getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(user, headers);
    }

    public void printCurrentBalance(){
        System.out.println(getBalance());
    }

    public void printUserList(){
        System.out.println();
    }

    public void printSendCheck(String error){
        if(error.equals("zero")) {
            System.out.println("Cannot send zero or negative amount");
        } else if(error.equals("success")){
            System.out.println("Send was successful");
        } else if(error.equals("amount")){
            System.out.println("Cannot Complete Transaction: Insufficient Funds");
        } else if(error.equals("self")){
            System.out.println("Cannot Complete Transaction: Cannot Send TEbucks to Self");
        } else {
            System.out.println("An unknown error has occurred");
        }
    }
}
