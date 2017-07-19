/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import it.csbeng.nice2mit.utils.URIs;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jackson.annotate.JsonIgnore;




/**
 *
 * @author Carmine Ingaldi
 */
public class ProfiloResource implements IResource
{
 
    private String nome;
    private String cognome;
    private String about;
    
    private String avatar;
    

    public ProfiloResource(String nome, String cognome, String about, ImageResource avatar)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.about = about;
        this.avatar = avatar == null ? "" : avatar.getRef();
    }

    public ProfiloResource()
    {    }
 
    
    

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

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(ImageResource avatar)
    {
        this.avatar = avatar.getRef();
    }

    @Override
    public String asURI()
    {
        return null;
    }


    
    

}
