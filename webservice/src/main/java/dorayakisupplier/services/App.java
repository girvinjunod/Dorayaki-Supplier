package dorayakisupplier.services;

import javax.xml.ws.Endpoint;

public class App 
{
    public static void main( String[] args )
    {
        Endpoint.publish("http://localhost:9999/webservice/dorayaki", new DorayakiServiceImpl());
        System.out.println( "Hello World!" );
    }
}
