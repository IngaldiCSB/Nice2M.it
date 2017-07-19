/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.resource;

import com.sun.jersey.spi.resource.Singleton;
import it.csbeng.nice2mit.service.ImageService;
import it.csbeng.nice2mit.service.resourceassembler.ImageAssembler;
import it.csbeng.nice2mit.utils.IOUtils;
import it.csbeng.nice2mit.utils.LRUMap;
import it.csbeng.nice2mit.utils.URIs;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Carmine Ingaldi
 */
@Path("images/{imgname}")
@Singleton
public class ImagesResource implements IResource
{
    @Context UriInfo uriInfo;

    
    private LRUMap <String , ImageResource> imgCache;

    public ImagesResource()
    {
        this.imgCache = new LRUMap (50);
    }

    @POST
//  @Consumes("image/jpeg")
    public Response addImage (@PathParam("imgname")String imgName)
    {
        
        
        ImageResource res = new ImageResource (imgName);
        
        new ImageService ("").addImage(new ImageAssembler(res));
        
        imgCache.put(imgName, res);
        
        
        //URI uri = uriInfo.getBaseUriBuilder().path("images").path(newImgName).build();
        return Response.ok(res , MediaType.APPLICATION_JSON).build();
        
   }
    
    @GET
    @Produces("image/jpeg")
    public Response getImage(@PathParam("imgname") String imgName)
    {
//        
//        ImageResource res = (ImageResource) this.imgCache.get(imgName);
//        byte [] imgData = res == null ? (new ImageService (ctx.getRealPath("/resources/imgs")).getImage(imgName).toResource()).getImgData() : res.getImgData();
//        
//        return Response.ok( imgData , "image/jpeg").header("Content-Length", res.getImgSize()).build();
        ImageResource res = new ImageService ("").getImage(imgName).toResource();
        return (res != null ?  Response.ok( res , "image/jpeg") : Response.status(Response.Status.NOT_FOUND) ).build();
        
    }
    
    @Override
    public String asURI()
    {
        return URIs.BASE_URI + "images/";
    }    
    
    

}
