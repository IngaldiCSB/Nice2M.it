/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.infrastructure.database;

import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.infrastructure.interfaces.IUtenteRepository;


/**
 *
 * @author Carmine Ingaldi
 */
public class UtenteRepository extends HibernateDAO <Utente , String> implements IUtenteRepository
{


}
