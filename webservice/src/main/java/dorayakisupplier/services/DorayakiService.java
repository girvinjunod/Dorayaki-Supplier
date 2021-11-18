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
    public String addRequest();

    @WebMethod
    public String getStatusRequest(int id_store);

    @WebMethod
    public String getAllRecipe();
}
