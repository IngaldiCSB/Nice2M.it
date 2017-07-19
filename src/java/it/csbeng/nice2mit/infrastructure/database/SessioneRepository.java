/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database;

import it.csbeng.nice2mit.entity.Sessione;
import it.csbeng.nice2mit.infrastructure.interfaces.ISessioneRepository;

/**
 *
 * @author Carmine Ingaldi
 */
public class SessioneRepository extends HibernateDAO <Sessione , Long> implements ISessioneRepository
{

}
