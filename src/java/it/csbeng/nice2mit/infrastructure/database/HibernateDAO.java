/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database;

import it.csbeng.nice2mit.entity.IEntity;
import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.infrastructure.interfaces.IRepository;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Carmine Ingaldi
 */
public abstract class HibernateDAO <Entity, ID> implements IRepository <Entity , ID>
{
    protected Session hibSess = HibernateSessionManager.INSTANCE.getSession();
    private Class<Entity> entityClass;

    public HibernateDAO()
    {
        this.entityClass = (Class<Entity>)( (ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    

    @Override
    public ID create(Entity entity)
    {
        hibSess.beginTransaction();
        ID i = (ID) hibSess.save(entity);
        hibSess.getTransaction().commit();
        
        return i;
    }

    @Override
    public Entity readById(ID id)
    {   if (!hibSess.isOpen()) System.out.println("sessione chiusa per la query");
       // hibSess.beginTransaction();
        Entity e = (Entity) hibSess.load(entityClass, (Serializable) id);
       // hibSess.getTransaction().commit();
        
        return e;
        
        
        
    }

    @Override
    public void update(Entity entity)
    {
        hibSess.beginTransaction();
        hibSess.update(entity);
        hibSess.getTransaction().commit();
        hibSess.flush();

    }

    @Override
    public void delete(ID id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Entity> readAll()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
