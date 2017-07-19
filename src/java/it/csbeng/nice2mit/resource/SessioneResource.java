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
public class SessioneResource implements IResource
{
    private Long id;
    private String utente;
    
    public SessioneResource(Long id , UtenteResource utente)
    {
        this.id = id;
        this.utente = utente.asURI();
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUtente()
    {
        return utente;
    }

    public void setUtente(String utente)
    {
        this.utente = utente;
    }
    
    

    @Override
    public String asURI()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
