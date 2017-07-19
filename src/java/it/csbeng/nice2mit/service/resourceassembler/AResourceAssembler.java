/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csbeng.nice2mit.service.resourceassembler;

import it.csbeng.nice2mit.resource.IResource;

/**
 *
 * @author gennaro
 */
public abstract class AResourceAssembler <Entity , Resource extends IResource>
{
    protected Entity e;
    protected Resource r;
    
    public AResourceAssembler (Entity e)
    {
        this.e = e;
        r = null;
    }
    
    public AResourceAssembler (Resource r)
    {
         this.r = r;
         e = null;
    }    
    
    public Entity toEntity ()
    {
        return r == null ? null : entityAssembly ();
    }
    
    protected abstract Entity entityAssembly ();
    
    public Resource toResource ()
    {
        return e == null ? null : resourceAssembly ();
    }

    protected abstract Resource resourceAssembly ();

    
}
