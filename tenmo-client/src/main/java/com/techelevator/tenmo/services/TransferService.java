package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferService {
    public static final String API_BASE_URL = "http://localhost:8080/user/transfer";
    private final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<Integer> makeEntity(Integer id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(id, headers);
    }

    public List<Transfer> getAllTransfers() {
        List<Transfer> myTransfers = new ArrayList<>();
        try {
            myTransfers = restTemplate.getForObject(API_BASE_URL + "/getAll", List.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public Transfer getTransferById(int id) {
        Transfer myTransfer = new Transfer();
        try {
            myTransfer = restTemplate.getForObject(API_BASE_URL + "/" + id, Transfer.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfer;
    }

    public List<Transfer> getTransfer(String username) {
        List<Transfer> myTransfers = new ArrayList<>();
        try {
            myTransfers = restTemplate.getForObject(API_BASE_URL + "?username_like=" + username, List.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public List<Transfer> getTransfer(int id) {
        List<Transfer> myTransfers = new ArrayList<>();
        try {
            myTransfers = restTemplate.getForObject(API_BASE_URL + "?user_id=" + id, List.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public List<Transfer> getTransfer(String username, int isFrom) {
        List<Transfer> myTransfers = new ArrayList<>();
        try {
            myTransfers = restTemplate.getForObject(API_BASE_URL + "?username_like=" + username + "?filter=" + isFrom, List.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

    public List<Transfer> getTransfer(int id, int isFrom) {
        List<Transfer> myTransfers = new ArrayList<>();
        try {
            myTransfers = restTemplate.getForObject(API_BASE_URL + "?user_id=" + id + "?filter=" + isFrom, List.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return myTransfers;
    }

}
