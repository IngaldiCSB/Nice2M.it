/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service;

import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.infrastructure.database.stubs.Stub_ImageRepository;
import it.csbeng.nice2mit.infrastructure.filesystem.ImageRepository;
import it.csbeng.nice2mit.infrastructure.interfaces.IImageRepository;
import it.csbeng.nice2mit.service.resourceassembler.AResourceAssembler;
import it.csbeng.nice2mit.service.resourceassembler.ImageAssembler;
import it.csbeng.nice2mit.utils.URIs;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carmine Ingaldi
 */
public class ImageService
{

    public ImageService(String resDirPath)
    {
    }
    
    public String addImage (AResourceAssembler imgAssembler)
    {
         
        Image i = (Image) imgAssembler.toEntity();
        i.setFilename(i.getFilename() + System.nanoTime());
        
        return i.getFilename();
        
    }
    

    
    public ImageAssembler getImage (String imgName)
    {
        return new ImageAssembler (new Image(imgName , 0 , null));
        
    }


    private void saveFile(InputStream uploadedInputStream,String serverLocation)
    {
        OutputStream outpuStream = null;
        try
        {
            outpuStream = new FileOutputStream(new File(serverLocation));

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1)
            {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                outpuStream.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(ImageService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }


}
