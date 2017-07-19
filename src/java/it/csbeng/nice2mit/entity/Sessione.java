/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Carmine Ingaldi
 */
@Entity
public class Sessione implements IEntity, Serializable
{
    @OneToOne(mappedBy = "sessioneAttiva" , cascade = CascadeType.ALL)
    private Utente utente;
    
    @Id
    @GeneratedValue
    private Long token;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startSess;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finishSess;
    
    
    //private List <Chiamata> chiamate;
    
    
    public Sessione ()
    {
        this.startSess = new Date ();
    }

    
    public Utente getUtente()
    {
        return utente;
    }

    public void setUtente(Utente utente)
    {
        this.utente = utente;
    }

    public Long getToken()
    {
        return token;
    }

    public void setToken (Long token)
    {
        this.token = token;
    }

    public Date getStartSess()
    {
        return startSess;
    }

    public void setStartSess()
    {
        this.startSess = new Date ();
    }

    public Date getFinishSess()
    {
        return finishSess;
    }

    public void setFinishSess(Date finishSess)
    {
        this.finishSess = new Date ();
    }
    
    

}
