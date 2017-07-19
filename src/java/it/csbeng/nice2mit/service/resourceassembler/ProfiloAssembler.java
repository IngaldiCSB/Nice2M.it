/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service.resourceassembler;

import it.csbeng.nice2mit.entity.Profilo;
import it.csbeng.nice2mit.resource.ProfiloResource;

/**
 *
 * @author Carmine Ingaldi
 */
public class ProfiloAssembler extends AResourceAssembler <Profilo , ProfiloResource>
{
    

    public ProfiloAssembler(Profilo e)
    {
        super(e);
    }

    public ProfiloAssembler(ProfiloResource r)
    {
        super(r);
    }
    

    @Override
    protected Profilo entityAssembly()
    {
        e = new Profilo ();
        
        e.setNome(r.getNome());
        e.setCognome(r.getCognome());
        e.setAbout(r.getAbout());
        
        return e;
    }

    @Override
    protected ProfiloResource resourceAssembly()
    {
        this.r = new ProfiloResource (e.getNome() , e.getCognome() , e.getAbout() , new ImageAssembler(e.getAvatar()).toResource());
        return r;
    }

}
