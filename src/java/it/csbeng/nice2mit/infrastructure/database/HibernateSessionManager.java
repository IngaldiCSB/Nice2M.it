/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database;

import it.csbeng.nice2mit.utils.CarmineHibernateUtil;
import org.hibernate.Session;

/**
 *
 * @author Carmine Ingaldi
 */
public enum HibernateSessionManager
{
    INSTANCE;
    
    private Session sess = null;
    
    public void openSession ()
    {
        sess = CarmineHibernateUtil.getSessionFactory().openSession();
    }
    
    public void closeSession ()
    {
        sess.close();
    }
    
    public Session getSession ()
    {
        return sess;
    }

            
}
