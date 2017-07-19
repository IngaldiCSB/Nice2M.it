/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit;

import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.entity.Profilo;
import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.utils.CarmineHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Carmine Ingaldi
 */
public class Nice2mitApp
{
    public static void main (String[] args)
    {

        Session sess = CarmineHibernateUtil.getSessionFactory().openSession();
        System.out.println("okok");
        
        Utente carmineCSB = new Utente("carmineCSB");       
        carmineCSB.setAuth(new Auth ("djcarmine@hotmail.it" , "password" ));
        carmineCSB.setProfilo(new Profilo("Carmine" , "Ingaldi" , "Nice2m.it Architect/full-stack developer" , null) );
        
        Utente oricc = new Utente("oricc");       
        oricc.setAuth(new Auth ("lucaungaro@hotmail.it" , "pwdluca" ));
        oricc.setProfilo(new Profilo("Luca" , "Ungaro" , "Nice2m.it front-end developer" , null) );
        
        carmineCSB.addAmico(oricc);
        
        //POPOLO IL DB
        Transaction tx = sess.beginTransaction();

        sess.save(oricc);
        sess.save(carmineCSB);
        
        tx.commit();
        sess.close();
    }

}
