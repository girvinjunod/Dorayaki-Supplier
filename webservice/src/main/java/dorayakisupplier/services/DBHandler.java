package dorayakisupplier.services;

import java.sql.*;

public class DBHandler {
    private Connection connection;
    private static String DB_URL= "jdbc:mysql://localhost:3306/dorayaki_factory";
    private static String DB_Username = "dorayaki_admin";
    private static String DB_Password = "dorayaki";

    public DBHandler(){
        try{
            System.out.println("Connecting to MySQL DB...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_Username, DB_Password);
            System.out.println("Database connected");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error on connecting to DB");
        }
    }

    public Connection getConnection(){
        return this.connection;
    }
}
