/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import it.csbeng.nice2mit.resource.DTOs.AuthDTO;
import it.csbeng.nice2mit.utils.URIs;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Carmine Ingaldi
 */


public class UtenteResource implements IResource
{
    
    
    private String username;
    private String profilo;
    private String amici;
    
    @JsonIgnore
    private ProfiloResource profiloRes;
    
    @JsonIgnore
    private AmiciResource amiciRes;
    
    @JsonIgnore
    private AuthDTO authRes;
    

    
        
    public UtenteResource (String username)
    {
        this.username = username;
        this.profilo = URIs.UTENTE_RES + "/" + username + URIs.PROFILO_SUBRES;
        this.amici = URIs.UTENTE_RES + "/" + username + URIs.AMICI_SUBRES;
        
        this.profiloRes = new ProfiloResource ();
        this.amiciRes = new AmiciResource ();
        

    }

    public String getProfilo()
    {
        return profilo;
    }

    public String getAmici()
    {
        return amici;
    }

    

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public ProfiloResource getProfiloRes()
    {
        return this.profiloRes;
    }

    public void setProfiloRes(ProfiloResource profiloRes)
    {
        this.profiloRes = profiloRes;
    }

    public void setAmiciRes(AmiciResource amiciRes)
    {
        this.amiciRes = amiciRes;
    }

    
    public AmiciResource getAmiciRes()
    {
        return this.amiciRes;
    }

    public AuthDTO getAuthRes()
    {
        return authRes;
    }

    public void setAuthRes(AuthDTO authRes)
    {
        this.authRes = authRes;
    }


    @Override
    public String asURI()
    {
        return URIs.BASE_URI + "/users/" + this.username;
    }

    
    
    
  
}
