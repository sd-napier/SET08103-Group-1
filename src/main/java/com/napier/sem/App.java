package com.napier.sem;

import java.sql.*;

/**
 * App class used for loading up database drivers -
 * and establishing connection to the database in preparation for interaction
 * App will attempt to connect 100 times to the database before failing connection
 */
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

        ///  This controller method is used for deployment, using correct connection parameters for the docker container. (ALL queries must run on 'db:3306')
        //cont.dockerTestConnection();

        ///  This controller method is used in development, to allow us to connect to the database locally. (ALL queries run on '127.0.0.1:33060')
        cont.LocalTestConnection();

    }

}