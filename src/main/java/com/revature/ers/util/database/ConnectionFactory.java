package com.revature.ers.util.database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/* Notice that this class is a Singleton design pattern. */
public class ConnectionFactory {
    /* instantiating Connection object */

    private static ConnectionFactory connectionFactory;

    /* instantiating Properties object to retrieve properties url, username, password */
  /*  private static final Properties prop = new Properties();*/

    static {
        try {
            /* importing the jdbc jar file into jvm */
            Class.forName("org.postgresql.Driver");

            /* using prop object to load url, username, password */
            /*prop.load(new FileReader("src/main/resources/db.properties"));*/

            /* actually getting this connection */
            /*con = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));*/

            /* throw Exception if connection was not successful */
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private Properties props = new Properties();



    private ConnectionFactory() {
        try {
            props.load(new FileReader("db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
    /* getter for connection */
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));

        if (conn == null) {
            throw new RuntimeException("Could not establish connection with the database!");
        }
        return conn;
    }

}
