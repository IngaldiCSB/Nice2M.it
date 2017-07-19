/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database.stubs;

import it.csbeng.nice2mit.entity.Sessione;
import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.infrastructure.interfaces.ISessioneRepository;
import java.util.List;

/**
 *
 * @author Carmine Ingaldi
 */
public class Stub_SessioneRepository implements ISessioneRepository
{

    @Override
    public Long create(Sessione entity)
    {
        System.out.println("creo sessione id" + entity.getToken().toString());
        
        return entity.getToken();
    }

    @Override
    public Sessione readById(Long id)
    {
        Sessione s = new Sessione ();
        s.setUtente(new Utente ());
        
        return s;
    }

    @Override
    public void update(Sessione entity)
    {
        System.out.println ("sessione aggiornata");
    }

    @Override
    public void delete(Long id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sessione> readAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
