/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database.stubs;

import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.infrastructure.interfaces.IImageRepository;
import it.csbeng.nice2mit.utils.IOUtils;
import it.csbeng.nice2mit.utils.URIs;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carmine Ingaldi
 */
public class Stub_ImageRepository implements IImageRepository
{
    public Stub_ImageRepository ()
    {
        String path = new File ("").getAbsolutePath();
        System.out.println(path);
    }

    private static Image image = null;
    
    @Override
    public String create(Image entity)
    {

        image = entity;
        try
        {
            IOUtils.writeFile(entity.getImg(), "C://Users//gennaro//Pictures//" + entity.getFilename() + ".jpg");
        }
        catch (IOException ex)
        {
            Logger.getLogger(Stub_ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entity.getFilename();
    }

    @Override
    public Image readById(String id)
    {
        File f = new File ("C://Users//gennaro//Pictures//" + (String)id + ".jpg");
        byte[] data; 
        try
        {
            data = IOUtils.readFile(f);
            return new Image ((String)id , data.length , data );
        }
        catch (IOException ex)
        {
            Logger.getLogger(Stub_ImageRepository.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void update(Image entity)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Image> readAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
