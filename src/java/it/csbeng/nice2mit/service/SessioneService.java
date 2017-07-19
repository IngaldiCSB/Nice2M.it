/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service;

import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.entity.Sessione;
import it.csbeng.nice2mit.infrastructure.database.AuthRepository;
import it.csbeng.nice2mit.infrastructure.database.SessioneRepository;
import it.csbeng.nice2mit.infrastructure.database.UtenteRepository;
import it.csbeng.nice2mit.infrastructure.database.stubs.Stub_SessioneRepository;
import it.csbeng.nice2mit.infrastructure.interfaces.ISessioneRepository;
import it.csbeng.nice2mit.service.resourceassembler.AResourceAssembler;
import it.csbeng.nice2mit.service.resourceassembler.SessioneAssembler;

/**
 *
 * @author Carmine Ingaldi
 */
public class SessioneService
{
    private ISessioneRepository sessRepo = new SessioneRepository ();
    
    public AResourceAssembler login (String email , String password)
    {
        Auth auth = new AuthRepository ().match(email, password);
        
        
        if (auth != null)
        {  
            Sessione sess = new Sessione ();
            sess.setUtente(auth.getUtente());
            
            auth.getUtente().setSessioneAttiva(sess);
            new UtenteRepository().update(auth.getUtente());
            
            
            sessRepo.create(sess);
            return new SessioneAssembler (sess);

        }
        else return new SessioneAssembler ( (Sessione)null );
    }
    


    
    public boolean logout (Long token)
    {
        Sessione sess = sessRepo.readById(token);
        
        if (sess != null)
        {
            sess.getUtente().setSessioneAttiva(null);
            sessRepo.update(sess);
            
            return true;
        }
        else return false;
    }
    
    public void startChiamata (Long token , String calleeUsername)
    {
        
    }
    
    public void stopChiamata (Long token , String calleeUsername)
    {
        
    }
    


}