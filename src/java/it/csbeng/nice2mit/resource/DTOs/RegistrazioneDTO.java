/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource.DTOs;

/**
 *
 * @author Carmine Ingaldi
 */
public class RegistrazioneDTO
{

    private String nome;
    private String cognome;
    private String about;
    
    private String avatar;
    
    private AuthDTO credenziali;

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


    public AuthDTO getCredenziali()
    {
        return credenziali;
    }

    public void setCredenziali(AuthDTO credenziali)
    {
        this.credenziali = credenziali;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }
    
    
    
    
    
    

}
