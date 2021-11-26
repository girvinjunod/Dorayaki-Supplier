package dorayakisupplier.services;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;


@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface DorayakiService {
    // @WebMethod
    // public String createDorayakiDatabase();

    // @WebMethod
    // public String addDorayaki();

    @WebMethod
    public String addRequest(String ip, String endpoint, int id_recipe, int count_request);

    @WebMethod
    public String[] getStatusRequest(String ip_store);

    @WebMethod
    public String[] getAllRecipe();

    @WebMethod
    public String test(String ip_store);
}
