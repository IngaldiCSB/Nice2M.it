
var static = require('node-static');
//Redis client per la persistenza
var redis = require('redis');

var store = redis.createClient();

var http = require('http');

// Create a node-static server instance
var file = new(static.Server)();

//Mantiene un elenco di nomi e un elenco di socket per i vari clients
var onlineUsers = [];


// We use the http module createServer function and
// rely on our instance of node-static to serve the files

var app = http.createServer(function (req, res) {
    file.serve(req, res);
    }).listen(8283);
	
// Use socket.io JavaScript library for real-time web applications
var io = require('socket.io').listen(app);
io.set('log level',1);
//Quando un utente si connette
io.sockets.on('connection', function (socket){
        //Il server memorizza chi è l'utente connesso e lo fa sapere agli altri
        socket.on('login',function(username){
            var user = {"username":username,"socket":socket};
            onlineUsers[user.username]=user;
            console.log(onlineUsers[user.username].username+' ha fatto il login ');
            //Viene aggiunto al set di utenti online
            store.sadd("onlineUsers", username);          
            socket.username = username;
            
            store.sinter('onlineUsers',username,function(err,users){
                    //Riceve l'elenco degli amici online 
                    socket.emit('receivewho',users);
                    //Per ogni amico online && tra gli amici
                    //invia che anche lui è online
                    if(users){
                        for(var j in users){
                            onlineUsers[users[j]].socket.emit('receivewho',username);
                        }}
            });            
        });
        
        socket.on('add friend',function(friendToAdd){
                //Se l'utente è online gli si invia la richiesta
                if(onlineUsers[friendToAdd]){            
                    var sock=onlineUsers[friendToAdd].socket;
                    sock.emit('ask friendship',socket.username);
                }
            });
        
        socket.on('ok to friendship',function(friend){
                //L'amico viene aggiunto al set con chiave username 
                //e valore friend
                store.sadd(socket.username,friend);
                //L'utente è aggiunto agli amici di friendtoadd
                store.sadd(friend,socket.username);
                if(onlineUsers[friend]){
                    //Notifico a entrambi che sono online
                    socket.emit('receive connected',friend);
                    var sock=onlineUsers[friend].socket;
                    sock.emit('added',socket.username);
                    sock.emit('receive connected',socket.username);
                }
                var options = {
                    hostname: '169.254.127.142',
                    port: 8084,
                    path: '/users/'+friend+'/amici/'+socket.username,
                    method: 'PUT',
                };
              /*  var req=http.request(options, function(res) {
                    console.log('Aggiunta in rest server con codice:'+res);});*/
        });
        
        socket.on('no to friendship',function(username){
            //L'utente non sa se l'amico ha accettato o meno
        });
            
        socket.on('disconnect',function(){
            var deleteUser = socket.username;
                //Rimuovo l'utente dalla lista onlineUsers
                if(deleteUser)
                store.srem('onlineUsers',deleteUser);                  
                console.log(deleteUser+' abbandona la chat');
                store.sinter('onlineUsers',socket.username,function(err,users){
                    //Se ci sono amici online
                    if(users){
                        //Notifico a tutti la mia disconnessione
                        for(var j in users){
                            onlineUsers[users[j]].socket.emit('disconnected',deleteUser);
                        }
                    }
                    //Rimuovo l'utente dalla lista in RAM            
                    var index = onlineUsers.indexOf(deleteUser);                
                    onlineUsers.splice(index,1);                   
                });
        });
        //Quando arriva un messaggio
	socket.on('message', function (message,callId) {
		console.log('send message: '+message+' on '+callId);
		socket.broadcast.to(callId).emit('message', message); 
                });
                
        //Quando un client fa il join a una chiamata     
	socket.on('join call',function(callId){
            var numClients = io.sockets.clients(callId).length;   
            if (numClients == 1) {
				// Second client joining...
				io.sockets.in(callId).emit('join', callId);
				socket.join(callId);
				socket.emit('joined', callId);
				} else { // max two clients
					socket.emit('unable to call', callId);
					}
        });	
	
	socket.on('call', function(userToCall){        
                var callId = socket.username+userToCall;
                console.log('Create ', callId);
                //Se l'utente è online lo chiamo
                    if(onlineUsers[userToCall]){
                        var sock=onlineUsers[userToCall].socket;
                        sock.emit('incoming call',callId);
                    }                
		var numClients = io.sockets.clients(callId).length;
		if (numClients == 0){
			socket.join(callId);
			socket.emit('created', callId);
			} else socket.emit('unable to call', callId);
					
	});
        //Gestisce l'abbandono della chiamata
        socket.on('leave',function(callId){
            console.log(socket.username+' abbandona '+callId);
            socket.leave(callId);
        });
        
});