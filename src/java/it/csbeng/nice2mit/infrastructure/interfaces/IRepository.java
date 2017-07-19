/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csbeng.nice2mit.infrastructure.interfaces;

import java.util.List;

/**
 *
 * @author gennaro
 */
public interface IRepository <Entity , ID>
{
    public ID create (Entity entity);
    public Entity readById (ID id);
    public void update (Entity entity);
    public void delete (ID id);
   
    public List<Entity> readAll ();
}
