package com.revature.ers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.models.User;
import com.revature.ers.services.UserService;
import com.revature.ers.util.annotations.Inject;
import com.revature.ers.util.custom_exceptions.InvalidRequestException;
import com.revature.ers.util.custom_exceptions.ResourceConflictException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Inject
    private final ObjectMapper mapper;
    private final UserService userService;

    @Inject
    public UserServlet(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {  //template to post in database
        try {
            NewUserRequest request = mapper.readValue(req.getInputStream(), NewUserRequest.class);
            User createdUser = userService.register(request);
            resp.setStatus(201); // CREATED
            resp.setContentType("application/json");
            resp.getWriter().write(mapper.writeValueAsString(createdUser.getId()));
        } catch (InvalidRequestException e) {
            resp.setStatus(404); // BAD REQUEST
        } catch (ResourceConflictException e) {
            resp.setStatus(409); // RESOURCE CONFLICT
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
