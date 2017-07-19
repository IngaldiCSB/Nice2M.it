/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import it.csbeng.nice2mit.resource.DTOs.AuthDTO;
import it.csbeng.nice2mit.service.SessioneService;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 *
 * @author Carmine Ingaldi
 */

@Path("/sessions")
public class SessioniResource
{    
    //richiesta post con email e password, il salvataggio genera un token e il token viene mandato al client con un cookie
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPost (AuthDTO auth)
    {
        SessioneService sess = new SessioneService ();
        SessioneResource res = (SessioneResource) sess.login(auth.getEmail(), auth.getPassword()).toResource();
        
        System.out.println("LOTA");
        
        
        if (res != null)
        {
            return Response.ok(res , MediaType.APPLICATION_JSON).cookie(new NewCookie ("sessid" , res.getId().toString() , null , "nice2m.it" , 1 , null , 0 , false)).build();
        }
        else
        {
            return Response.status(403).build();
        }

    }
    
    @DELETE
    public Response doDelete(@CookieParam("sessid") Long token)
    {
        
        return (new SessioneService ().logout(token) ? Response.ok() : Response.status(403)).build() ;
        
    }
    
    
    @POST
    @Path("call")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startCall (@CookieParam("sessid") Long token , String calleeUsername)
    {
        new SessioneService ().startChiamata(token, calleeUsername);
        return null;
        
    }
    
    @PUT
    @Path("call")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response hangupCall (@CookieParam("sessid") Long token , String calleeUsername)
    {
        new SessioneService ().stopChiamata(token, calleeUsername);
        return null;
        
    }


}
