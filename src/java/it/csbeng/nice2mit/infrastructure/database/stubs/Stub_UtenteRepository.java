/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database.stubs;

import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.entity.Profilo;
import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.infrastructure.interfaces.IRepository;
import it.csbeng.nice2mit.infrastructure.interfaces.IUtenteRepository;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carmine Ingaldi
 */
public class Stub_UtenteRepository implements IUtenteRepository
{

    @Override
    public String create(Utente entity)
    {
        System.out.println (entity.getUsername());
        return entity.getUsername();
    }

    @Override
    public Utente readById(String id)
    {
        Utente u = new Utente ((String) id);
        u.setProfilo(new Profilo ("carmine" , "ingaldi" , "robbabbon" , new Image("avatar33932638742897" , 100 , null) ));
        u.setAmici(new ArrayList ());
        u.addAmico(new Utente ("luchett"));
        u.addAmico(new Utente ("oderooo"));
        return u;
    }

    @Override
    public void update(Utente entity)
    {
        System.out.println ("updated: " + entity);
    }

    @Override
    public void delete(String id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Utente> readAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
