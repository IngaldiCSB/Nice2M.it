/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.filesystem;

import it.csbeng.nice2mit.infrastructure.interfaces.IRepository;
import it.csbeng.nice2mit.utils.IOUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Carmine Ingaldi
 */

public class FilesystemDAO
{
    private final String dirPath;

    public FilesystemDAO(String dirPath)
    {
        this.dirPath = dirPath;
    }
    
    public void writeFile (byte [] buffer , String filename)
    {
        FileOutputStream fos = null;
        File f = new File(dirPath + "\\" + filename);
        
        System.out.println(dirPath + "\\" + filename);
        
        try
        {
            if (!f.exists())
            {
                f.createNewFile();
            }
            
            fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FilesystemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }            
            
    }
    
    public byte[] readFile(String filename)
    {

        File file = new File(dirPath + "\\" + filename);

        
        byte []buffer = new byte[(int) file.length()];
        InputStream ios = null;
                
        try
        {
            ios = new FileInputStream(file);
            if ( ios.read(buffer) == -1 )
            {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(FilesystemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(FilesystemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            
            if ( ios != null )
            {
                try
                {
                    ios.close();
                }
                catch (IOException ex)
                {
                    Logger.getLogger(FilesystemDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

        }
        
        return buffer;
    }
 
}
