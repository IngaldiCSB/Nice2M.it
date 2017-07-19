/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database;

import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.infrastructure.interfaces.IAuthRepository;
import java.util.ArrayList;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Carmine Ingaldi
 */
public class AuthRepository extends HibernateDAO <Auth , String> implements IAuthRepository
{

    @Override
    public Auth match(String email, String password)
    {
//        ArrayList<Auth> auths = (ArrayList<Auth>) super.hibSess.createCriteria(Auth.class).add(Restrictions.eq("email", email)).add(Restrictions.eq("password", email)).list();
//        
//        return auths.size() == 1 ? auths.get(0) : null;
        
        Auth auth = readById(email);
        return auth.getPassword().equals(password) ? auth : null;
    }

}
