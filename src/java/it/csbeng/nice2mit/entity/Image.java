/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.entity;

import java.io.File;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *
 * @author Carmine Ingaldi
 */

@Entity
public class Image implements IEntity
{
    @Id
    private String filename;
    
    private int imgSize;
    
    @Transient
    private byte[] img;
    @OneToOne(mappedBy = "avatar")
    private Profilo profilo;

    public Image(String filename, int imgSize, byte[] img)
    {
        this.filename = filename;
        this.imgSize = imgSize;
        this.img = img;
    }

    
    public Image ()
    {}
    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public int getImgSize()
    {
        return imgSize;
    }

    public void setImgSize(int size)
    {
        this.imgSize = size;
    }

    public byte[] getImg()
    {
        return img;
    }

    public void setImg(byte[] img)
    {
        this.img = img;
    }

 
    
    
    
    
    
    

}
