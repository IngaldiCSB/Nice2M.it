/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import it.csbeng.nice2mit.utils.URIs;

/**
 *
 * @author Carmine Ingaldi
 */
public class AmicoResource implements IResource
{
    private String username;
    private String url;

    public AmicoResource(String username)
    {
        this.username = username;
        this.url = URIs.UTENTE_RES + "/" + username;
    }
    

    public String getUsername()
    {
        return username;
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public String asURI()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
