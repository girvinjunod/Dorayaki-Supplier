package dorayakisupplier.services;

import javax.jws.WebService;

import java.sql.*;
import java.util.Date;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

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
    public String addRequest(String ip, String endpoint, int id_recipe, int count_request){
        try{
            System.out.print(ip);
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
            String formattedSQL = String.format(sql,ip,endpoint, timestamp);
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
                String formattedSQLrequestlog = String.format(sqlrequestlog,ip, endpoint);
                int reqreslog = statement.executeUpdate(formattedSQLrequestlog);
                System.out.println("Request logged " + Integer.toString(reqreslog));

                // Input ke request
                String sqlrequest = "INSERT INTO request (ip_store, status_request, id_recipe, count_request, updated) VALUES('%s', '%s', %d, %d, %d);";
                String formattedSQLrequest = String.format(sqlrequest,ip,"WAITING",id_recipe, count_request, 0);
                int reqres = statement.executeUpdate(formattedSQLrequest);
                System.out.println("Request sent " + Integer.toString(reqres));

                // kontak backend buat email
                // Create an instance of HttpClient.
                HttpClient httpClient = HttpClients.createDefault();

                // Create a method instance.
                HttpGet get = new HttpGet("http://localhost:4000/sendEmail");
                try{
                    HttpResponse response = httpClient.execute(get);
                    System.out.println(response);
                } catch(Exception e){
                    e.printStackTrace();
                    return "Something went wrong, " + e.getMessage();
                }

                System.out.println("Request sent, please check your email");
                return "1"; 
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Something went wrong, " + e.getMessage());
            return "0";
        }
    }

    @Override
    public String[] getStatusRequest(String ip_store){
      try{
        System.out.println(ip_store);
        DBHandler handler= new DBHandler();
        Connection conn = handler.getConnection();
        Statement statement = conn.createStatement();

        String sql = "SELECT * FROM request WHERE ip_store='%s' AND status_request='ACCEPTED' AND updated=0";
        String countSQL = "SELECT count(*) FROM request WHERE ip_store='%s' AND status_request='ACCEPTED' AND updated=0";
        String updateSQL = "UPDATE request SET updated=1 WHERE ip_store='%s' AND status_request='ACCEPTED' AND updated=0";
        
        String formattedSQL = String.format(sql, ip_store);
        String countFormattedSQL = String.format(countSQL, ip_store);
        String updateFormattedSQL = String.format(updateSQL, ip_store);

        System.out.println(formattedSQL);
        System.out.println(countFormattedSQL);
        System.out.println(updateFormattedSQL);
        
        ResultSet countResult = statement.executeQuery(countFormattedSQL);

        int count = 0;
        while(countResult.next()){
          count = countResult.getInt(1);
        }
        countResult.close();
        System.out.println(count);
        ResultSet result = statement.executeQuery(formattedSQL);
        String[] hasilQuery = new String[count]  ;  
        int i = 0;
        while(result.next()){
          String dataArray = String.valueOf(result.getInt(4)) + ";" + String.valueOf(result.getInt(5)) ;
          System.out.println(dataArray);
          hasilQuery[i] = dataArray ;
          i++;
        }
        int updateResultLog = statement.executeUpdate(updateFormattedSQL);
        System.out.println("Table Updated: " + Integer.toString(updateResultLog));
        System.out.println("anjer");
        
        return hasilQuery;
        
      } catch(Exception e){
          System.out.println(e);
          String[] mangga = new String[0];
          return mangga;
      }
    }

    @Override
    public String getAllRecipe(){
        // Create an instance of HttpClient.
        HttpClient httpClient = HttpClients.createDefault();

        // Create a method instance.
        HttpGet get = new HttpGet("http://localhost:4000/getAllRecipe");
        try{
            HttpResponse response = httpClient.execute(get);
            System.out.println(response);
        } catch(Exception e){
            e.printStackTrace();
            return "Something went wrong, " + e.getMessage();
        }

        // handle response dan oper ke store
        return "get all recipe";
    }

    @Override
    public String test(String ip_store){
        // Create an instance of HttpClient.
        System.out.println(ip_store);
        return ip_store;
        // handle response dan oper ke store
        // return "get all recipe";
    }
}
