angular.module('nice2mitAPP.webrtc_connectors', ['nice2mitAPP.services2'])
        .service('dataChannelService',dataChannelService);

function dataChannelService($q,mySocket){
    var sendIsReady=false;
    var receiveIsReady=false;
    var sendChannel=null;
    var receiveChannel=null;
    var sdpConstraints = {};
    var pc=null;
    var isInit;
    var self = this;
    var d = null;
    //FUNZIONE PER L'INVIO DEI MESSAGGI
    this.sendData = function(data){
        if(isInit)sendChannel.send(data);
        else receiveChannel.send(data);
    };

   
    //FUNZIONE PER SETTARE L'HANDLE DELLA RICEZIONE/INVIO MESSAGGI
    /*Un esempio:
     *  function handleMessage(event) {
     *       var receiveTextarea = document.querySelector("#dataChannelReceive");
     *       receiveTextarea.value += event.data + '\n';
     *   } 
     */
    this.handleMessage=null;
    
    
    //CREA IL DATACHANNEL E PRENDE IN INGRESSO L'UTENTE CON CUI CHATTARE 
    //E UN BOOLEANO A INDICARE SE CHI CHIAMA E' L'INITIATOR
    //NB:SAPERE CHI E' L'INITIATOR SERVE PER DARE IL VIA ALLA CHAT CON L'EMIT
    //   DA PARTE DI UNO SOLO DEI PEER
    this.createDataChannel=function(){
        d=$q.defer();
        
        if(receiveChannel && sendChannel){
            
            return $q.when({'sendChannel':sendChannel,'receiveChannel':receiveChannel});}
        else{
            try {
                var label = Math.random(); 
                pc = new RTCPeerConnection();
                sendChannel = pc.createDataChannel(label,
                {reliable: true});
            } catch (e) {
                alert('Failed to create data channel. ');
                d.reject();
            }
            
            sendChannel.onopen = handleSendChannelStateChange;
            
            sendChannel.onclose = handleSendChannelStateChange; 
            
            pc.ondatachannel = gotReceiveChannel;
            
            pc.onicecandidate = handleIceCandidate;
            
            
            
            
        }
        //----------------------------------------------------------------------------//
        // dataChannel Handlers                                                       //
        //----------------------------------------------------------------------------//
        
        
        
        function handleReceiveChannelStateChange() {
            var readyState = receiveChannel.readyState;
            trace('Receive channel state is: ' + readyState);
            // If channel ready, enable user's input
            if (readyState === "open") {
                receiveIsReady=true; 
                if(sendIsReady && receiveIsReady){
                    d.resolve({'sendChannel':sendChannel,'receiveChannel':receiveChannel});
                    return d.promise;
                }
            };
        };
        
        function handleSendChannelStateChange() {
            var readyState = sendChannel.readyState;
            trace('Send channel state is: ' + readyState);
            // If channel ready, enable user's input
            if (readyState === "open") {
                sendIsReady=true;
                if(sendIsReady && receiveIsReady){
                    d.resolve({'sendChannel':sendChannel,'receiveChannel':receiveChannel});
                    return d.promise;
                }
            } 
        };
        function handleIceCandidate(event) {
            console.log('handleIceCandidate event: ', event);
            if (event.candidate) {
                mySocket.emit('message',{
                    type: 'candidate',
                    label: event.candidate.sdpMLineIndex,
                    id: event.candidate.sdpMid,
                    candidate: event.candidate.candidate});
            } else {
                console.log('End of candidates.');
            }
        }
        
        function gotReceiveChannel(event) {
            trace('Receive Channel Callback');
            receiveChannel = event.channel;
            alert('receiveChannel'+receiveChannel);
            receiveChannel.onmessage = handleMessage;
            receiveChannel.onopen = handleReceiveChannelStateChange;
            receiveChannel.onclose = handleReceiveChannelStateChange;
        };
        
    return d.promise;

    };
    
    this.chatWith = function (userToChat)
    {
      mySocket.emit('chat',userToChat); 
    };
//----------------------------------------------------------------------------//
//                                                                            //
//----------------------------------------------------------------------------// 

    mySocket.on('request to chat',function(user){
            self.createDataChannel(user,false);
            pc.createOffer(function(sessionDescription) {
                pc.setLocalDescription(sessionDescription);
                mySocket.emit('message', sessionDescription);
                },
                function(error){
                    console.log('Failed to create signaling message : ' + error.name);
                },
                sdpConstraints
            );         
    });
    
    mySocket.on('created', function (callId){
	console.log('Created calling ' + callId);
        }
    );
    
    mySocket.on('message',function(message){
        if (message.type === 'offer') {
            pc.setRemoteDescription(new RTCSessionDescription(message));
            console.log('Sending answer to peer.');
            pc.createAnswer(function(sessionDescription) {
                                pc.setLocalDescription(sessionDescription);
                                mySocket.emit('message',sessionDescription);
                            },
                            function(error){
                                console.log('Failed to create signaling message : ' + error.name);
                            },
                            sdpConstraints
                            );
                    
	} else if (message.type === 'answer') {
            console.log('Received answer '+message);
            pc.setRemoteDescription(new RTCSessionDescription(message),
                                        function(){
                                            console.log("Success remote descripton once received answer");
                                        }
                                    );
                } else if (message.type === 'candidate') {
			var candidate = new RTCIceCandidate({sdpMLineIndex:message.label,
						candidate:message.candidate});
                        pc.addIceCandidate(candidate);
			}
    });

       
};


