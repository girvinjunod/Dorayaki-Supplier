package dorayakisupplier.services;

import javax.jws.WebService;

import java.sql.*;

@WebService(endpointInterface = "dorayakisupplier.services.DorayakiService")
public class DorayakiServiceImpl implements DorayakiService {
    @Override
    public String createDorayakiDatabase(){
        try{
            DBHandler handler= new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String sql = "CREATE TABLE testjava (id INT, apel VARCHAR(255));";
            int count  = statement.executeUpdate(sql);
            return "Success with value: " + Integer.toString(count);
        }
        catch (Exception e){
            e.printStackTrace();
            return "Something went wrong, " + e.getMessage();
        }
    }

    @Override
    public String addDorayaki(){
        try{
            DBHandler handler= new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO testjava (id, apel) VALUES(%d, %s);";
            String formattedSQL = String.format(sql,69,"mangga");
            int count  = statement.executeUpdate(formattedSQL);
            return "Add dorayaki success with value: " + Integer.toString(count);
        }
        catch (Exception e){
            e.printStackTrace();
            return "Something went wrong, " + e.getMessage();
        }
    }
}
