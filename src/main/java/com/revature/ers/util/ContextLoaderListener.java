package com.revature.ers.util;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.daos.UserDAO;
import com.revature.ers.services.TokenService;
import com.revature.ers.services.UserService;
import com.revature.ers.servlets.AuthServlet;
import com.revature.ers.servlets.UserServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/* Need this ContextLoaderListener for our dependency injection upon deployment. */
public class ContextLoaderListener implements ServletContextListener { //this is to map servlet and models. mapping dependency injection. Used to authenticate when user logsin or sign up
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("\nInitializing ERS web application");

        /* ObjectMapper provides functionality for reading and writing JSON, either to and from basic POJOs (Plain Old Java Objects) */
        ObjectMapper mapper = new ObjectMapper();

        /* Dependency injection. */
        UserServlet userServlet = new UserServlet(mapper, new UserService(new UserDAO()), new TokenService(new JwtConfig()));
        AuthServlet authServlet = new AuthServlet(mapper, new UserService(new UserDAO()), new TokenService(new JwtConfig()));

        /* Need ServletContext class to map whatever servlet to url path. */
        ServletContext context = sce.getServletContext();
        context.addServlet("UserServlet", userServlet).addMapping("/users/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("\nShutting down ERS web application");
    }
}