package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args) {

        Controller cont = new Controller();
        //cont.dockerTestConnection();
        cont.LocalTestConnection();
    }

}