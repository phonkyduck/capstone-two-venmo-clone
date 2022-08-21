package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private BigDecimal accountBalance;

    public static final  String API_BASE_URL = "http://localhost:8080/user/account/";
    private final RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getBalance(){
        accountBalance = restTemplate.getForObject(API_BASE_URL, BigDecimal.class);
        return accountBalance;
    }

    public String sendTE(User fromUser, User toUser, BigDecimal amount){
        String error = "";
        try {
            if(fromUser != toUser && accountBalance.compareTo(amount) >= 0) {
                restTemplate.put(API_BASE_URL + "/" + toUser.getId() + "-" + fromUser.getId(), makeEntity(amount));
                error = "success";
            } else if (fromUser != toUser && accountBalance.compareTo(amount) < 0){
                error = "amount";
            } else if (fromUser == toUser && accountBalance.compareTo(amount) >= 0){
                error = "self";
            } else { error = "unknown";}
        } catch(RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return error;
    }

    private HttpEntity<BigDecimal> makeEntity(BigDecimal amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(amount, headers);
    }

    public void printCurrentBalance(){
        System.out.println(getBalance());
    }

    public void printUserList(){
        System.out.println();
    }

    public void printSendCheck(String error){
        if(error.equals("success")){
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
