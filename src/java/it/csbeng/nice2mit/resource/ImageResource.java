/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import it.csbeng.nice2mit.utils.URIs;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Carmine Ingaldi
 */
public class ImageResource implements IResource
{

    private String ref;

    @Context ServletContext ctx;


    public ImageResource(String ref)
    {
        this.ref = ref;

    }


    public String getRef()
    {
        return "SaveImage/" + this.ref;
    }

    public void setRef(String ref)
    {
        this.ref = ref;
    }

    @Override
    public String asURI()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
