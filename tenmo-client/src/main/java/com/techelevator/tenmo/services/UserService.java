package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.apiguardian.api.API;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public static final String API_BASE_URL = "http://localhost:8080/user";
    private final RestTemplate restTemplate = new RestTemplate();
    private final User user = new User();

    private HttpEntity<User> makeEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(user, headers);
    }

    public User[] getUsers(){
        User[] availableUsers = new User[]{};
        try {
            availableUsers = restTemplate.getForObject(API_BASE_URL, User[].class);
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
