/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carmine Ingaldi
 */
public class IOUtils
{
    public static byte[] readInputStream (InputStream is , int arraySize) throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        
        int i;
        byte[] data = new byte[arraySize];
        
        while ((i = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, i);
        }
        
        buffer.flush();
        
        return buffer.toByteArray();
    }
    
    public static File writeFile (byte [] buffer , String filename) throws IOException
    {
        FileOutputStream fos = null;
        File f = new File(filename);
        
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

        return f;
            
            
    }
     
    public static byte[] readFile(File file) throws IOException
    {
        
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
        finally
        {
            
            if ( ios != null )
            {
                ios.close();
                
            }

        }
        
        return buffer;
}
    

}
