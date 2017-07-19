/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csbeng.nice2mit.infrastructure.interfaces;

import it.csbeng.nice2mit.entity.Auth;

/**
 *
 * @author Carmine Ingaldi
 */
public interface IAuthRepository extends IRepository <Auth , String> 
{    
    public Auth match (String email , String password);
}
