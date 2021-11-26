package dorayakisupplier.services;

import javax.xml.ws.Endpoint;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class App {
    public static void main(String[] args) {
        Endpoint.publish("http://0.0.0.0:8080/webservice/apelmanggakucing", new DorayakiServiceImpl());
        System.out.println("Hello World!");
        Dotenv dotenv = Dotenv.load();
        System.out.println("Hostname: " + dotenv.get("HOSTNAME"));
        System.out.println("Mysql Host: " + dotenv.get("MYSQL_HOST"));
    }
}
