/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import it.csbeng.nice2mit.utils.URIs;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonUnwrapped;

/**
 *
 * @author Carmine Ingaldi
 */
public class AmiciResource implements IResource
{
    @JsonUnwrapped
    private List<AmicoResource> amici = new ArrayList();
    
    public AmiciResource ()
    {}
    
    public void addAmico (String username)
    {
        amici.add(new AmicoResource (username));
    }
    
    public List<AmicoResource> getAmici ()
    {
        return amici;
    }

    @Override
    public String asURI()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
