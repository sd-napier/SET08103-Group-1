package com.napier.sem;

import java.sql.*;

public class App
{
    /** Main Method - Starting point for the Application
     * @since Oct 2025
     * @author Stuart C. Alexander
     * @param args
     * @throws SQLException -- can maybe add custom exception handling at a later date to gracefully handle errors.
     */
    public static void main(String[] args) throws SQLException {

        ///  Create new instance of the Controller Class... then run one of two methods below...
        Controller cont = new Controller();

        ///  This controller method is used for deployment, using correct connection parameters for the docker container. (ALL queries must run method 'runQuery()' on 'db:3306')
        cont.dockerTestConnection();

        ///  This controller method is used in development, to allow us to connect to the database locally. (ALL queries must run method 'runQueryLocal()' on '127.0.0.1:33060')
        //cont.LocalTestConnection();

    }

}