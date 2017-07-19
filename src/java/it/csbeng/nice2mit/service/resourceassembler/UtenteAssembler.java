/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service.resourceassembler;

import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.resource.UtenteResource;

/**
 *
 * @author Carmine Ingaldi
 */
public class UtenteAssembler extends AResourceAssembler <Utente , UtenteResource>
{

    public UtenteAssembler(Utente e)
    {
        super(e);
    }

    public UtenteAssembler(UtenteResource r)
    {
        super(r);
    }
    

    @Override
    protected Utente entityAssembly()
    {
        e.setUsername(r.getUsername());
        e.setProfilo( new ProfiloAssembler (r.getProfiloRes() ).toEntity() );
        e.setAmici( new AmiciAssembler (r.getAmiciRes()).toEntity());
        
        
        return e;  
    }

    @Override
    protected UtenteResource resourceAssembly()
    {
        r = new UtenteResource (e.getUsername());
        r.setProfiloRes( (new ProfiloAssembler (e.getProfilo())).toResource());
        r.setAmiciRes(new AmiciAssembler (e.getAmici()).toResource());
                
        return r;
    }

 
}
