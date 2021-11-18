package dorayakisupplier.services;

import javax.jws.WebService;

import java.sql.*;
import java.util.Date;
import java.util.Calendar;

@WebService(endpointInterface = "dorayakisupplier.services.DorayakiService")
public class DorayakiServiceImpl implements DorayakiService {
    // @Override
    // public String createDorayakiDatabase(){
    //     try{
    //         DBHandler handler= new DBHandler();
    //         Connection conn = handler.getConnection();
    //         Statement statement = conn.createStatement();
    //         String sql = "CREATE TABLE testjava (id INT, apel VARCHAR(255));";
    //         int count  = statement.executeUpdate(sql);
    //         return "Success with value: " + Integer.toString(count);
    //     }
    //     catch (Exception e){
    //         e.printStackTrace();
    //         return "Something went wrong, " + e.getMessage();
    //     }
    // }

    // @Override
    // public String addDorayaki(){
    //     try{
    //         DBHandler handler= new DBHandler();
    //         Connection conn = handler.getConnection();
    //         Statement statement = conn.createStatement();
    //         String sql = "INSERT INTO testjava (id, apel) VALUES(%d, '%s');";
    //         String formattedSQL = String.format(sql,69,"mangga");
    //         int count  = statement.executeUpdate(formattedSQL);
    //         return "Add dorayaki success with value: " + Integer.toString(count);
    //     }
    //     catch (Exception e){
    //         e.printStackTrace();
    //         return "Something went wrong, " + e.getMessage();
    //     }
    // }

    @Override
    public String addRequest(){
        try{
            // dipindahin jadi param
            String temp_ip = "1.2.3.4.5";
            String temp_endpoint = "somewhere";
            int temp_id_recipe = 1;
            int temp_count_request = 7;


            DBHandler handler= new DBHandler();
            Connection conn = handler.getConnection();
            Statement statement = conn.createStatement();

            Timestamp timestamp = new Timestamp(new Date().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp.getTime());

            // max 10 request dalam 30 detik
            cal.add(Calendar.SECOND, -30);
            timestamp = new Timestamp(cal.getTime().getTime());

            String sql = "SELECT count(*) FROM log_request WHERE ip_store='%s' AND endpoint_request='%s' AND time_request > '%s'";
            String formattedSQL = String.format(sql,temp_ip,temp_endpoint, timestamp);
            ResultSet result = statement.executeQuery(formattedSQL);
            System.out.println(result);
            int count = 0;
            while (result.next()){
                count = Integer.parseInt(result.getString(1));
                System.out.println(count);
            }

            if (count > 10){
                System.out.println("Too much request");
                return "Too much request, requst cancelled";
            } else{
                System.out.println("Inputting request into request table");
                // Input ke log request
                String sqlrequestlog = "INSERT INTO log_request (ip_store, endpoint_request) VALUES('%s', '%s');";
                String formattedSQLrequestlog = String.format(sqlrequestlog,temp_ip, temp_endpoint);
                int reqreslog = statement.executeUpdate(formattedSQLrequestlog);
                System.out.println("Request logged " + Integer.toString(reqreslog));

                // Input ke request
                String sqlrequest = "INSERT INTO request (ip_store, status_request, id_recipe, count_request) VALUES('%s', '%s', %d, %d);";
                String formattedSQLrequest = String.format(sqlrequest,temp_ip,"WAITING",temp_id_recipe, temp_count_request);
                int reqres = statement.executeUpdate(formattedSQLrequest);
                System.out.println("Request sent " + Integer.toString(reqres));

                // kontak backend buat email


                return "Request sent, please check your email";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "Something went wrong, " + e.getMessage();
        }
    }

    @Override
    public String getStatusRequest(int id_store){
        return "get status request";
    }

    @Override
    public String getAllRecipe(){
        return "get all recipe";
    }
}
