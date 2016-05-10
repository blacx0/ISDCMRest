/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import Controller.ConnectionJDBC;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

// El servicio estarÃ¡ en el path factorial
@Stateless
@Path("/views")

public class Views {
    
    // Responde al mÃ©todo GET y recibe el parÃ¡metro base por el QueryString
    @GET
    public String getViews(@QueryParam("id") int id) {
        ConnectionJDBC.connect();
        int v = ConnectionJDBC.getViewsById(id);
        ConnectionJDBC.disconnect();
        return Integer.toString(v);
    }
    
    @POST
    public String addView(@QueryParam("id") int id) {
        System.out.print(id);
        ConnectionJDBC.connect();
        int v = ConnectionJDBC.addView(id);
        ConnectionJDBC.disconnect();
        return Integer.toString(v);
    }
}
