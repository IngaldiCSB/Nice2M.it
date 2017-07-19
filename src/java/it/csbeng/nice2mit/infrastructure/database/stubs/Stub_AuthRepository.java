/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database.stubs;

import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.entity.Profilo;
import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.infrastructure.interfaces.IAuthRepository;
import it.csbeng.nice2mit.infrastructure.interfaces.IRepository;
import java.util.ArrayList;
import java.util.List;


public class Stub_AuthRepository implements IAuthRepository 
{

    @Override
    public String create(Auth entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Auth readById(String id)
    {
        Utente u = new Utente ("username");
        u.setProfilo(new Profilo ("carmine" , "ingaldi" , "robbabbon" , null) );
        u.setAmici(new ArrayList ());
        u.addAmico(new Utente ("luchett"));
        u.addAmico(new Utente ("oderooo"));
        
        Auth auth = new Auth ((String) id, "password");
        u.setAuth(auth);
        auth.setUtente(u);
        
        return auth;
    }

    @Override
    public void update(Auth entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Auth> readAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Auth match(String email, String password)
    {
        return password.equals("password") ? this.readById(email) : null;
    }

     
}
