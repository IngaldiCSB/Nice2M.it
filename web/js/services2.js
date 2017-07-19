'use strict';

/* Services */

angular.module('nice2mitAPP.services2', ['btford.socket-io']).
        
        service('sessionService' , ['$http' , sessionService]).
        service('userService' , [ '$http' , '$q' , 'sessionService' ,  userService]).
        service('profileService' , [ '$http' , '$q' , 'userService' ,  profileService]).
        service('friendsService' , friendsService)
        .factory('mySocket', function (socketFactory)
        {
            var mySocket;
            
            try 
            {
                mySocket= io.connect('http://169.254.67.174:8283');
                mySocket = socketFactory({ioSocket: mySocket });
            }
            catch(err)
            {
                    log("Fallita la connessione al signaling proxy");
                    mySocket = null;
            }
            return mySocket;
        });


//------------------------------------------------------------------------------

//service che incapsula le informazioni di sessione
//usare dove si è interessati a sapere se l'utente è loggato e conoscerne
//user e sessid
function sessionService ($http)
{
    var session = null;
    var logged = false;
    
    var promise;

    this.resetSessionStatus = function ()
    {
        session = null;
        logged = false;
    };
    
    //OK
    this.getSession = function ()
    {
        return promise.then(function(){return session;});
    };
    
    //effettua la richiesta al server per l'autenticazione
    //se l'autenticazione va a buon fine, setta 'logged' a true
    //e assegna il payload della risposta a 'sessionStatus'
    //OK    
    this.newSession = function (auth)
    {
        promise = $http.post('restAPI/sessions' , auth).then(function(data)
        {
            session = data.data;
            logged = true;

            
            return session;
        });
        
        return promise;
    };    
    
    //OK
    this.isLogged = function ()
    {
        return logged;
    };

       
            
}

//incapsula la risorsa utente, dipende da sessione
function userService ($http , $q , sessionService)
{
    var user = null;
    
    
    
    this.setUser = function (_user)
    {
        user = _user;
    };

    this.getUser = function ()
    {
        
        
        var deferred = $q.defer();
        
        if (user === null)
        {
                   
            sessionService.getSession().then(function(sessionData)
            {
                return $http.get(sessionData.utente);
            }).then(function(data)
            {
                user = data.data;
                
                deferred.resolve(user);
            });                
        }
        else
        {
            deferred.resolve(user);
        }
        
        return deferred.promise;

    };
}

//incapsula la risorsa profilo, dipende da utente
function profileService ($http , $q , userService)
{
    var profile = null;
    
    this.getProfile = function ()
    {
        var deferred = $q.defer();
        
        if (profile === null)
        {
            userService.getUser().then(function(userData)
            {
                return $http.get(userData.profilo);
                
            }).then(function(data)
            {
                profile = data.data;
                deferred.resolve(profile);
            });                   
             
        }
        else
        {
            deferred.resolve(profile);
        }
        
        return deferred.promise;

    };
    
    //qui invece ci fai la POST per la registrazione
    this.setProfile = function (_profile)
    {
        //questo ci vuole
        profile = _profile;
        
    };
}

//service che incapsula la lista amici di un utente loggato. usare per manipolare
//la lista di amici e sapere se un utente è amico dell'utente loggato
function friendsService ($http , $q , userService, $rootScope)
{   
    var FriendList= null;
    
    var self = this;
    this.deferred = $q.defer();
    
    
    this.setFriendList = function (_Friendlist)
    {
        FriendList= _Friendlist;
    };
    
    //qua sempre la stessa cosa
    this.getFriendList = function ()
    {
        
        if (FriendList === null)
        {
            userService.getUser().then(function(userData)
            {
                return $http.get(userData.amici);
                
            }).then(function(data)
            {
                FriendList = data.data.amici;
                
                for (var i in FriendList)
                {
                        FriendList[i].online = false;
                }
                
                self.deferred.resolve(FriendList);
            });                   
            
        }
        else
        {
            self.deferred.resolve(FriendList);
        }
        
        return self.deferred.promise;

    };
    
    //OPZIONALE!! richiede al server le info su un amico e ritorna
    //nome, cognome e immagine dell'amico, cosi possiamo popolare un piccolo
    //popup che appare quando passi il mouse sul nickname dell'amico, tipo facebook
    this.getFriendPreview = function (friendname)
    {
        
    };
    
    //fai la richiesta al server di aggiungere l'amico
    //ma ricorda di aggiungerlo anche a friendlist
    this.addFriend = function (friendname)
    {        
        var toAdd = {'username' : friendname , 'link' : 'users/' + friendname , 'online' : false };
 
        if(!FriendList)
        {
            FriendList = new Array ();
        }   

        FriendList.push(toAdd);
        self.deferred.resolve(FriendList);

    };
    
    //OK
    this.isFriend = function (friendname)
    {
        return FriendList.filter(function(i){return i.username === friendname;}).length !== 0;   
    };
    
    this.setFriendOffline = function (friendname)
    {
        FriendList.filter(function(i){return i.username === friendname;})[0].online = false;
        self.deferred.resolve(FriendList);
    };
    
    this.setFriendOnline = function (friendname)
    {
        FriendList.filter(function(i){return i.username === friendname;})[0].online = true;
        self.deferred.resolve(FriendList);

    };
    
    this.isOnline = function (friendname)
    {
      return FriendList.filter(function(i){return i.username === friendname;})[0].online;  
      
    };
    

}
//------------------------------------------------------------------------------