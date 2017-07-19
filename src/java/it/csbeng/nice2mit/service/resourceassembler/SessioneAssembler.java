/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service.resourceassembler;

import it.csbeng.nice2mit.entity.Sessione;
import it.csbeng.nice2mit.resource.SessioneResource;

/**
 *
 * @author Carmine Ingaldi
 */
public class SessioneAssembler extends AResourceAssembler <Sessione , SessioneResource>
{

    public SessioneAssembler(Sessione e)
    {
        super(e);
    }

    public SessioneAssembler(SessioneResource r)
    {
        super(r);
    }
    

    @Override
    protected Sessione entityAssembly()
    {
        e = new Sessione ();
        
        e.setToken(r.getId());
        e.setUtente(null);
        
        
        return e;
    }

    @Override
    protected SessioneResource resourceAssembly()
    {
        r = new SessioneResource (e.getToken() , new UtenteAssembler(e.getUtente()).toResource());
        
        return r;
    }

}
