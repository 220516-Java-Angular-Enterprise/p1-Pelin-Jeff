package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.User;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.util.annotations.Inject;
import com.revature.ers.util.custom_exceptions.InvalidRequestException;
import com.revature.ers.util.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class UserServlet extends HttpServlet {
    @Inject
    private final ObjectMapper mapper;
    private final UserService userService;

    private final TokenService tokenService;

    @Inject
    public UserServlet(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  //template to post in database
        try {
            NewUserRequest userRequest = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            String[] uris = req.getRequestURI().split("/");

            if (uris.length == 4 && uris[3].equals("username")) {
                Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

                if (requester == null) {
                    resp.setStatus(401); //Unauthorized
                    return;
                }

                if (!requester.getRole().equals("ADMIN")) {
                    resp.setStatus(403); //Forbidden
                    return;
                }

                if (userRequest.getUsername().equals("")) {
                    resp.setStatus(404);
                    return;
                }

                List<User> users = userService.getUserByUsername(userRequest.getUsername());
                resp.setContentType("application/json");
                resp.getWriter().write(mapper.writeValueAsString(users));
                return;
            }

            User createdUser = userService.register(userRequest);
            resp.setStatus(201); //Created
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(createdUser.getId()));
         }  catch (InvalidRequestException e) {
            resp.setStatus(404); //Bad Request
        }   catch (ResourceConflictException e) {
            resp.setStatus(409); //resource conflict
        }   catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
}

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Principal requester = tokenService.extractRequesterDetails(req.getHeader("Authorization"));

        if (requester == null) {
            resp.setStatus(401); //Unauthorized
            return;
        }

        if (!requester.getRole().equals("ADMIN")) {
            resp.setStatus(403); //Forbidden
            return;
        }

        List<User> users = userService.getAllUsers();
        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString(users));
    }
}
