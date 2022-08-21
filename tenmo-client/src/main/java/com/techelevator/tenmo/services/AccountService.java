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

    public boolean sendTE(User fromUser, User toUser, BigDecimal amount){
        boolean successful = false;
        try {
            restTemplate.put(API_BASE_URL + "/" + toUser.getId() + "-" + fromUser.getId(), makeEntity(amount));
            successful = true;
        } catch(RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return successful;
    }

    private HttpEntity<BigDecimal> makeEntity(BigDecimal amount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(amount, headers);
    }

}
