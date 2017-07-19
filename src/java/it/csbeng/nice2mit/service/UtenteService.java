/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.service;

import it.csbeng.nice2mit.entity.Auth;
import it.csbeng.nice2mit.entity.Image;
import it.csbeng.nice2mit.entity.Profilo;
import it.csbeng.nice2mit.entity.Sessione;
import it.csbeng.nice2mit.entity.Utente;
import it.csbeng.nice2mit.infrastructure.database.HibernateDAO;
import it.csbeng.nice2mit.infrastructure.database.SessioneRepository;
import it.csbeng.nice2mit.infrastructure.database.UtenteRepository;
import it.csbeng.nice2mit.infrastructure.database.stubs.Stub_ImageRepository;
import it.csbeng.nice2mit.infrastructure.filesystem.ImageRepository;
import it.csbeng.nice2mit.infrastructure.interfaces.IImageRepository;
import it.csbeng.nice2mit.infrastructure.interfaces.ISessioneRepository;
import it.csbeng.nice2mit.resource.DTOs.RegistrazioneDTO;
import it.csbeng.nice2mit.service.resourceassembler.AResourceAssembler;
import it.csbeng.nice2mit.service.resourceassembler.UtenteAssembler;
import it.csbeng.nice2mit.utils.URIs;

/**
 *
 * @author Carmine Ingaldi
 */
public class UtenteService
{
    UtenteRepository utenteRepo = new UtenteRepository ();
    IImageRepository imgRepo = new ImageRepository (URIs.IMG_PATH);
    ISessioneRepository sessRepo = new SessioneRepository ();


    public boolean registrazione (String username , RegistrazioneDTO dto)
    {
        Utente utente = new Utente (username);
        
        utente.setAuth(new Auth (dto.getCredenziali().getEmail() , dto.getCredenziali().getPassword()) );
        utente.setProfilo(new Profilo(dto.getNome() , dto.getCognome() , dto.getAbout() , new Image(dto.getAvatar() , 0 , null) ));
        
             
        utenteRepo.create(utente);
        
        return true;
    
    }
    
    
    public AResourceAssembler cercaUtente (String username)
    {

        return new UtenteAssembler ((Utente) utenteRepo.readById(username));

    }


    //TODO mannaccia la madonna
    public boolean aggiungiAmico(String username, String amicoUsername)
    {
        Utente u = utenteRepo.readById(username);
        Utente amico = utenteRepo.readById(amicoUsername);
        
        if(u != null && amico != null)
        {
            if(!u.getUsername().equals(amico.getUsername()))
            {
                u.addAmico(amico);
                
                utenteRepo.update(u);                
            }

            return true;
        }
        else return false;
    }


}
