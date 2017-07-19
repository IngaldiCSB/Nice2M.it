/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.entity;

import it.csbeng.nice2mit.infrastructure.database.HibernateSessionManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Carmine Ingaldi
 */

@Entity
public class Utente implements IEntity, Serializable
{
    @Id
    private String username;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Auth auth;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Profilo profilo;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    private Sessione sessioneAttiva;
    


    @ManyToMany(mappedBy = "amicoDi")
    private List<Utente> amici = new ArrayList();
    
    @ManyToMany
    private List<Utente> amicoDi = new ArrayList();
    


    public Utente ()
    {}
    
    public Utente(String username)
    {
        this.username = username;
    }

    public Sessione getActiveSession()
    {
        return sessioneAttiva;
    }

    public void setActiveSession(Sessione activeSession)
    {
        this.sessioneAttiva = activeSession;
    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }


    public Profilo getProfilo()
    {
        return profilo;
    }

    public void setProfilo(Profilo profilo)
    {
        this.profilo = profilo;
    }   
    
    public void addAmico (Utente u)
    {
        this.amici.add(u);
        u.addAmicoDi(this);
        System.out.println("amico aggunto");

    }
    
    private void addAmicoDi (Utente amico)
    {
        this.amicoDi.add(amico);
    }

    public void setAmici(List<Utente> amici)
    {
        this.amici = amici;
    }
    
    public List<Utente> getAmici()
    {
        List<Utente> out = new ArrayList ();
        
        if (!HibernateSessionManager.INSTANCE.getSession().isOpen()) System.out.println("sessione chiusa");
        out.addAll(amici);
        out.addAll(amicoDi);
        return out;
    }   
    
    public void setSessioneAttiva (Sessione activeSession)
    {
        this.sessioneAttiva = activeSession;
    }
    
    public Sessione getSessioneAttiva ()
    {
        return this.sessioneAttiva;
    }

    
    public Auth getAuth()
    {
        return auth;
    }

    public void setAuth(Auth auth)
    {
        this.auth = auth;
    }


    
    
    

}
