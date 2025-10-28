package com.napier.sem;

import java.sql.*;

public class App
{


    public static void main(String[] args) {

        GUI gui = new GUI();
        gui.setVisible(true);

        Queries q = new Queries();
        int x = q.testDBConnection();

//        if (x == 1) {
//            System.out.println("CONNECTED... STARTING UI....");
//            GUI gui = new GUI();
//            gui.setVisible(true);
//        } else {
//            System.out.println("FAILED...EXITING APPLICATION");
//        }
    }
}