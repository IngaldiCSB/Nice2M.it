/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Carmine Ingaldi
 */
@Entity
public class Profilo implements IEntity
{
    @Id
    private String nome;
    private String cognome;
    private String about;
    
    @OneToOne(mappedBy = "profilo")
    private Utente utente;
    
    @OneToOne(cascade=CascadeType.ALL)
    private Image avatar;

    public Profilo()
    {
    }
    
    //TODO inserire image
    public Profilo(String nome, String cognome, String about , Image avatar)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.about = about;
        this.avatar = avatar;
    }
    
    

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCognome()
    {
        return cognome;
    }

    public void setCognome(String cognome)
    {
        this.cognome = cognome;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public Utente getUtente()
    {
        return utente;
    }

    public void setUtente(Utente utente)
    {
        this.utente = utente;
    }

    public Image getAvatar()
    {
        return avatar;
    }

    public void setAvatar(Image avatar)
    {
        this.avatar = avatar;
    }
    
}
