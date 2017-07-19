/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csbeng.nice2mit.utils;



import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.entity.Chiamata;
import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.entity.Profilo;
import it.csbeng.nice2mit.entity.Sessione;
import it.csbeng.nice2mit.entity.Utente;
import java.util.logging.Level;
import javax.persistence.Table;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author gennaro
 */
public class CarmineHibernateUtil
{
    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/nice2mitdb?zeroDateTimeBehavior=convertToNull";
    public static final String username = "root";
    public static final String password = "admin";

    private static final SessionFactory sessionFactory;
    private static Session sess = null;
    
    static {
        try {
            
            java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
            
            Configuration config = new AnnotationConfiguration().configure()
            .setProperty("hibernate.connection.driver_class", driver)
            .setProperty("hibernate.connection.url", URL)
            .setProperty("hibernate.connection.username", username)
            .setProperty("hibernate.connection.password", password)
            .setProperty("hibernate.hbm2ddl.auto", "update")
                    
                    .addAnnotatedClass(Auth.class)
                    .addAnnotatedClass(Utente.class)
                    .addAnnotatedClass(Profilo.class)
                    .addAnnotatedClass(Image.class)
                    .addAnnotatedClass(Sessione.class);
//                    .addAnnotatedClass(Chiamata.class);

            
            sessionFactory = config.buildSessionFactory();
            
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    public static  String  getTableName (Class entity)
    {
        Table ann = (Table) entity.getAnnotation(Table.class);
        
        return ann == null ? entity.toString() : ann.name();
               
    }

    public static void testConnection() throws HibernateException {
        
        Session s = sessionFactory.openSession();
        if (s.isConnected())
        {
            System.out.println("Hibernate works");
        }
        
        s.close();
    }
    
    public static Session getSession ()
    {
        if (sess == null)
        {
            sess = CarmineHibernateUtil.getSessionFactory().openSession();
        }
        
        return sess;
    }
    
    public static void closeSession ()
    {
        sess.close();
        sess = null;
    }
       
    
    
}
