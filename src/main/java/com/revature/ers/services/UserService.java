package com.revature.ers.services;

import com.revature.ers.daos.UserDAO;
import com.revature.ers.dtos.requests.LoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.models.User;
import com.revature.ers.util.annotations.Inject;
import com.revature.ers.util.custom_exceptions.AuthenticationException;
import com.revature.ers.util.custom_exceptions.InvalidRequestException;
import com.revature.ers.util.custom_exceptions.InvalidUserException;
import com.revature.ers.util.custom_exceptions.ResourceConflictException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Purpose: validation ie. checks username, password, and retrieve data from our daos. */
public class UserService {

    @Inject
    private final UserDAO userDAO;

    @Inject
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User login(LoginRequest request) {
        /* List<User> users = new ArrayList<>() */
        /* users = userDAO.getAll() */

        User user = new User();
        if(!isValidUsername(request.getUsername()) || !isValidPassword(request.getPassword())) throw new InvalidRequestException("Invalid username or password");
        user = userDAO.getUserByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (user == null) throw new AuthenticationException("Invalid credentials provided!");
        return user;
    }

    public User register(NewUserRequest request) {
        User user = request.extractUser();

        if (isNotDuplicateUsername(user.getUsername())) {
            if (isValidUsername(user.getUsername())) {
                if (isValidPassword(user.getPassword())) {
                    if(isValidEmail(user.getEmail())) {
                        if(isValidFirstName(user.getGiven_name())) {
                            if(isValidLastName(user.getSurname())) {
                                user.setUsername(request.getUsername());
                                user.setPassword(request.getPassword());
                                user.setEmail(request.getEmail());
                                user.setGiven_name(request.getFirstName());
                                user.setSurname(request.getLastName());
                                user.setId(UUID.randomUUID().toString());
                                userDAO.save(user);
                            } else throw new InvalidRequestException("Invalid last name. Last name needs to be 8-25 characters long.");
                        } else throw new InvalidRequestException("Invalid first name. First name needs to be 8-25 characters long.");
                    } else throw new InvalidRequestException("Invalid email.");
                } else throw new InvalidRequestException("Invalid password. Minimum eight characters, at least one letter, one number and one special character.");
            } else throw new InvalidRequestException("Invalid username. Username needs to be 8-20 characters long.");
        } else throw new ResourceConflictException("Username is already taken :(");

        return user;
    }

    public List<User> getAllUsers(){
        return userDAO.getAll();
    }

    public List<User> getUserByUsername(String name) {
        return userDAO.getUsersByUsername(name);
    }

 /*   public User getUserById(String id) {
        return userDAO.getById(id);
    }*/

    private boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }

    private boolean isNotDuplicateUsername(String username) {
        return !userDAO.getAllUsernames().contains(username);
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private boolean isValidFirstName(String name) {
        return name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }

    private boolean isValidLastName(String name) {
        return name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }

/*    private User isValidCredentials(User user) {
        if (user.getUsername() == null && user.getPassword() == null)
            throw new InvalidUserException("Incorrect username and password.");
        else if (user.getUsername() == null) throw new InvalidUserException("Incorrect username.");
        else if (user.getPassword() == null) throw new InvalidUserException("Incorrect password.");

        return user;
    }*/
}
