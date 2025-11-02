package com.napier.sem;

import java.sql.*;

/**
 * App class used for loading up database drivers -
 * and establishing connection to the database in preparation for interaction
 * App will attempt to connect 100 times to the database before failing connection
 */
public class App
{
    public static void main(String[] args) {

        Controller cont = new Controller();
        cont.dockerTestConnection();
        //cont.LocalTestConnection();
    }

}