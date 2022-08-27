package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.apiguardian.api.API;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public static final String API_BASE_URL = "http://localhost:8080/user";
    private final RestTemplate restTemplate = new RestTemplate();
    private String token;

    public User getUser() {
        return user;
    }

    private User user = new User();

    public String getToken() {
        return token;
    }

    public UserService(String token, User user){
        this.token = token;
        this.user = user;
    }

    private HttpEntity<User> makeEntity() {
        User user = getUser();
        String token = getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(user, headers);
    }

    public User[] getUsers(){
        User[] availableUsers = new User[]{};
        try {
            ResponseEntity<User[]> response =
            restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeEntity(), User[].class);
            availableUsers = response.getBody();
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return availableUsers;
    }

    public User findUserByString(String username){
        User foundUser = new User();
        User[] searchList;
        searchList = getUsers();
        for (User user: searchList) {
            if(user.getUsername() == username){
                foundUser = user;
            }
        }
        if(foundUser.getUsername() == ""){
            throw new IllegalArgumentException("Not a valid user");
        }
        return foundUser;
    }

}
