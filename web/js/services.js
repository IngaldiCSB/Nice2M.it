'use strict';

/* Services */

angular.module('nice2mitAPP.services', []).
        
        service('sessionService' , ['$http' , sessionService]).
        service('userService' , [ userService]).
        service('profileService' , [ profileService]).
        service('friendsService' , ['$http' , 'userService' , friendsService]);


//------------------------------------------------------------------------------

//service che incapsula le informazioni di sessione
//usare dove si è interessati a sapere se l'utente è loggato e conoscerne
//user e sessid
function sessionService ($http)
{
    
    var sessionStatus = null;
    var logged = false;
    
    //effettua la richiesta al server per l'autenticazione
    //se l'autenticazione va a buon fine, setta 'logged' a true
    //e assegna il payload della risposta a 'sessionStatus'
    //OK
    this.resetSessionStatus = function ()
    {
        sessionStatus = null;
        logged = false;
    };
    
    //OK
    this.getSession = function ()
    {
        return sessionStatus;
    };
    
    this.setSession = function (_sessionStatus)
    {
        sessionStatus = _sessionStatus;
    };    
    
    //OK
    this.isLogged = function ()
    {
        return logged;
    };
    
    this.setLogged = function (_logged)
    {
        logged = _logged;       
    };
       
            
}

//incapsula la risorsa utente, dipende da sessione
function userService ()
{
    var userStatus = null;
    
    this.setUser = function (user)
    {
        userStatus = user;
    };

    this.getUser = function ()
    {
        return userStatus;        
    };
}

//incapsula la risorsa profilo, dipende da utente
function profileService ()
{
    var profileStatus = null;
    
    this.getProfile = function ()
    {
        return profileStatus;
    };
    
    //qui invece ci fai la POST per la registrazione
    this.setProfile = function (profile)
    {
        //questo ci vuole
        profileStatus = profile;
        
        if (profileStatus === null) alert ("profilo resettato");
    };
}

//service che incapsula la lista amici di un utente loggato. usare per manipolare
//la lista di amici e sapere se un utente è amico dell'utente loggato
function friendsService ($http , userService)
{   
    var FriendList=null;
    
    //TEEEEST toglia sti rui sciem e metti null
    this.setFriendList = function (friendlist)
    {
        FriendList=friendlist;
    };
    
    //qua sempre la stessa cosa
    this.getFriendList = function ()
    {
        return FriendList;
    };
    
    //OPZIONALE!! richiede al server le info su un amico e ritorna
    //nome, cognome e immagine dell'amico, cosi possiamo popolare un piccolo
    //popup che appare quando passi il mouse sul nickname dell'amico, tipo facebook
    this.getFriendPreview = function (friendname)
    {
        
    };
    
    //fai la richiesta al server di aggiungere l'amico
    //ma ricorda di aggiungerlo anche a friendlist
    this.addFriend = function ()
    {
        
    };
    
    //OK
    this.isFriend = function (friendname)
    {
        return FriendList.filter(function(i){return i.username === friendname;}).length !== 0;   
    };

}
//------------------------------------------------------------------------------