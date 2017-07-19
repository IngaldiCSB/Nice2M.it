/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service.resourceassembler;

import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.resource.ImageResource;

/**
 *
 * @author Carmine Ingaldi
 */
public class ImageAssembler extends AResourceAssembler <Image , ImageResource>
{

    public ImageAssembler(Image e)
    {
        super(e);
    }

    public ImageAssembler(ImageResource r)
    {
        super(r);
    }
    

    @Override
    protected Image entityAssembly()
    {
        return new Image (r.getRef() , 0 , null);
    }

    @Override
    protected ImageResource resourceAssembly()
    {
        return new ImageResource (e.getFilename());
    }

}
