angular.module('nice2mitAPP.presence', ['btford.socket-io','nice2mitAPP.services2'])
        .service('PresenceService',PresenceService);


function PresenceService($q, mySocket , friendsService)
{
    
    var myName=null;
    
    
    /**
     * Risponde all'evento richiesta di amicizia
     * 
     * @param {handle} handleReceiveFriendshipRequest callback che può prendere
     *                  come ingresso l'username di chi effettua la richiesta
     */
     mySocket.on('ask friendship' , function(user)
     {
                alert("richiesta di amicizia ricevuta da" + user);
                this.acceptFriendship(user);
        }); 
   
    
    /**
     * Risponde all'evento scatenato quando l'amico accetta la richiesta di amicizia
     * 
     * @param {handle} handleFriendAdded può prendere come ingresso l'amico che
     *                  è stato aggiunto
     */    
    mySocket.on('added',function (user)
    {   

        friendsService.addFriend(user);

    });
    
    
    /**
     * Risponde all'evento scatento quando un amico si disconnette
     * 
     * @param {handle} handleDisconnectedFriend può prendere in ingresso il nome
     *                      di chi si è disconnesso  
     */
    mySocket.on('disconnected', function(user)
    {        
        alert(user + 'si è disconnesso');

            friendsService.setFriendOffline(user);

    });
    
    
    /**
     * Risponde all'evento scatento quando uno o più amici si connettono
     * 
     * @param {handle} handleReceiveConnected può prendere in ingresso un unico
     *                      nome di chi si è connesso o un array di nomi se 
     *                      all'atto del login ci sono già amici online
     *                       
     */

        mySocket.on('receive connected', function (users)
        {
            alert (users + 'è connesso');
            
            for (var i in users)
            {
                friendsService.setFriendOnline(users[i]);
            }
        });

        
        
    
    this.login=function(myname){

        if(myName)
            return $q.when(myName);
            else{
                var d=$q.defer();
                try{
                    if(myname){
                        myName = myname;
                        mySocket.emit('login',myname);
                        d.resolve(myName);
                        return d.promise;
                    }
                    else {
                        d.reject('no name');
                        return;
                    }
                }catch(e){
                    d.reject(e);
                    return;
                }
            }
    
    };
    this.addFriend=function(userToAdd){
                if(userToAdd){
                    mySocket.emit('add friend',userToAdd);    
        }
    };
    
    /**
     * Rifiuta una richiesta di amicizia
     * @param {string} username nome utente di chi si rifiuta
     * 
     */
    this.rejectFriendship=function(username){
        mySocket.emit('no to friendship',username);
    };
    /**
     * Accetta una richiesta di amicizia
     * @param {string} username nome utente di chi si accetta
     * 
     */    
    this.acceptFriendship=function(username){
        mySocket.emit('ok to friendship',username);    
    };
    
}