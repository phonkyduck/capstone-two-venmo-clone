package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    //Variables

    private BigDecimal accountBalance;
    public static final  String API_BASE_URL = "http://localhost:8080/user/account";
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    //Methods

    public AccountService(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }

    public BigDecimal getBalance(){
        ResponseEntity <BigDecimal> response =
        restTemplate.exchange(API_BASE_URL + "/", HttpMethod.GET, makeEntity(), BigDecimal.class);
        accountBalance = response.getBody();
        return accountBalance;
    }

    private HttpEntity<User> makeEntity() {
        User user = currentUser.getUser();
        String token = currentUser.getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(user, headers);
    }

    public void printCurrentBalance(){
        String balance = String.valueOf(getBalance());
        String stars ="*********************************";
        System.out.printf("%s %n%s %31s %n* %s %11s * %n%s %31s %n%s",stars,"*","*","Your balance is: ", balance,"*","*",stars);
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
    public void printRequestCheck(String error){
        if(error.equals("zero")) {
            System.out.println("Cannot send zero or negative amount");
        } else if(error.equals("success")){
            System.out.println("Request was successful");
        } else if(error.equals("self")){
            System.out.println("Cannot Complete Request: Sender and Recipient cannot be same User");
        } else {
            System.out.println("An unknown error has occurred");
        }
    }
}
