
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import com.sun.jersey.spi.resource.Singleton;
import it.csbeng.nice2mit.resource.DTOs.RegistrazioneDTO;
import it.csbeng.nice2mit.service.UtenteService;
import it.csbeng.nice2mit.service.resourceassembler.UtenteAssembler;
import it.csbeng.nice2mit.utils.CarmineHibernateUtil;
import it.csbeng.nice2mit.utils.LRUMap;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.hibernate.Session;

/**
 *
 * @author Carmine Ingaldi
 */
@Singleton
@Path("users/{username}")
public class UtentiResource
{


    @Context UriInfo uriInfo;
    private LRUMap <String , UtenteResource> utentiCache;

    public UtentiResource()
    {
        this.utentiCache = new LRUMap (50);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost (@PathParam("username") String username , RegistrazioneDTO reg)
    {
        boolean esito = new UtenteService ().registrazione(username, reg);
        
        //dopo la registrazione effettuo il login
        
        return esito ?  Response.ok().build() : Response.serverError().build();
         
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UtenteResource doGet (@PathParam("username") String username)
    {

        UtenteResource res = this.getUtenteResource();

        return res;
        
    }
 
    
    @Path("amici")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AmiciResource getAmici ()
    {
        return this.getUtenteResource().getAmiciRes();
    }
    
   
    @Path("amici/{amicoUsername}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAmico (@PathParam("username") String username , @PathParam("amicoUsername")String amicoUsername)
    {
        utentiCache.remove(username);
        return (new UtenteService().aggiungiAmico(username  , amicoUsername) ? Response.ok() : Response.status(403)).build() ;
        
    }

    
    @Path ("profile")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ProfiloResource getProfilo()
    { 
        return this.getUtenteResource().getProfiloRes();
    }
    
    //TODO questo è una zuppona
    @Path ("profile")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setProfilo(RegistrazioneDTO dto)
    { 
       return Response.ok().build();
    }

    private UtenteResource getUtenteResource ()
    {
        String username = uriInfo.getPathParameters().getFirst("username");

        UtenteResource ures = (UtenteResource) this.utentiCache.get(username);
        
        return (UtenteResource) (ures == null ? new UtenteService().cercaUtente(username).toResource() : ures);
        

    }
    
    private void invalidateUtenteResource ()
    {
        String username = uriInfo.getPathParameters().getFirst("username");
        utentiCache.remove(username);
        
    }
}
