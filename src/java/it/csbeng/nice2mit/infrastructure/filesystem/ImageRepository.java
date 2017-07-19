/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.filesystem;

import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.infrastructure.interfaces.IImageRepository;
import java.util.List;

/**
 *
 * @author Carmine Ingaldi
 */
public class ImageRepository implements IImageRepository
{
    private FilesystemDAO dao;

    public ImageRepository(String path)
    {
        this.dao = new FilesystemDAO(path);
    }
    
    
    
    @Override
    public String create(Image entity)
    {
        dao.writeFile(entity.getImg(), entity.getFilename() + ".jpg");
        return entity.getFilename();
    }

    @Override
    public Image readById(String id)
    {
        byte[] data = dao.readFile(id + ".jpg" );
        return new Image (id, data.length , data );
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
