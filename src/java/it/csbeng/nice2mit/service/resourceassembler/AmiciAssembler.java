/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service.resourceassembler;

import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.resource.AmiciResource;
import java.util.List;

/**
 *
 * @author Carmine Ingaldi
 */
public class AmiciAssembler extends AResourceAssembler <List<Utente> , AmiciResource>
{

    public AmiciAssembler(List<Utente> e)
    {
        super(e);
    }

    public AmiciAssembler(AmiciResource r)
    {
        super(r);
    }

    
    @Override
    protected List<Utente> entityAssembly()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected AmiciResource resourceAssembly()
    {
        r = new AmiciResource ();
        for (Utente i : e)
        {
            r.addAmico(i.getUsername());
        }
        
        return r;
    }

}
