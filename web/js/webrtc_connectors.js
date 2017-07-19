/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

angular.module('nice2mitAPP.webrtc_connectors', ['btford.socket-io'])
        .service('VideoService',VideoService);

function VideoService(mySocket){
    var isChannelReady = false;
    var isInitiator = false;
    var mycall=null;
    var localStream=null;
    var pc=null;
    var sendChannel=null;
    var receiveChannel=null;
    var remoteStream=null;
    var constraints= {video: true, audio: true};
    var isStarted = false;
    var onmessageHdl = function (){};
    var pc_config = webrtcDetectedBrowser === 'firefox' ?
    {'iceServers':[{'url':'stun:23.21.150.121'}]} : // IP address
    {'iceServers': [{'url': 'stun:stun1.l.google.com:19302'}]};
    
    var pc_constraints = {
	'optional': [
            {'DtlsSrtpKeyAgreement': true}
	]};
    
    // Session Description Protocol constraints:
    var sdpConstraints = {};
    
    this.call=function(userToCall)
    {
        mySocket.emit('call',userToCall);
    };
    
    
    this.onmessage = function (handler)
    {
        onmessageHdl = handler;
    };     
    
    this.sendData=function(data)
    {
        
        if(isInitiator) sendChannel.send(data);
        else receiveChannel.send(data);
        
    };
    
    this.initVideo=function(handler)
    {
        
        mySocket.on('join', function (callId)
        {
            console.log('The callie wants to join ' + callId);
            alert('Richiesta accettata');
            isChannelReady = true;
        });
        // Handle 'joined' message coming back from server:
        // this is the second peer joining the channel
        mySocket.on('joined', function (callId){
            console.log('This peer has joined call ' + callId);
            isChannelReady = true;
            // Call getUserMedia()
            constraints = {video: true, audio: true};
            navigator.getUserMedia(constraints, handleUserMedia, handleUserMediaError);
            console.log('Getting user media with constraints', constraints);
        });
        
        mySocket.on('incoming call',function(callId){   
            mycall=callId;
            alert("Sei stato invitato a partecipare alla chiamata "+mycall);
            //supponiamo che ha accettato, ma si deve premere un button per accettare
            mySocket.emit('join call',mycall);
            handler();
        });
        
        mySocket.on('message', function (message,callId){
            console.log('Received message:', message);
            if (message=== 'got user media') {
                checkAndStart();
            } else if (message.type === 'offer') {
                if (!isInitiator && !isStarted) {
                    checkAndStart();
                }
                pc.setRemoteDescription(new RTCSessionDescription(message));
                doAnswer();
            } else if (message.type === 'answer' && isStarted) {
                console.log('Received answer '+message);
                pc.setRemoteDescription(new RTCSessionDescription(message));
            } else if (message.type === 'candidate' && isStarted) {
                var candidate = new RTCIceCandidate({sdpMLineIndex:message.label,
                    candidate:message.candidate});
                pc.addIceCandidate(candidate);
            }
        });
        
        mySocket.on('created', function (callId){
            console.log('Created calling ' + callId);
            isInitiator = true;
            mycall=callId;
            alert("Avvio chiamata "+mycall);
            // Set getUserMedia constraints
            isChannelReady=true;
            // Call getUserMedia()
            navigator.getUserMedia(constraints, handleUserMedia, handleUserMediaError);
            console.log('Getting user media with constraints', constraints);
            checkAndStart();
        });
        
        function handleUserMedia(stream) {
            localStream = stream;
            attachMediaStream(document.querySelector('#localVideo'), stream);
            console.log('Adding local stream.');
            sendMessage('got user media');
        }
        function handleUserMediaError(error){
            console.log('navigator.getUserMedia error: ', error);
        }
        function checkAndStart() {
            //alert('isStarted '+isStarted + '- typeof localstream '+ typeof localstream + '- isChannelReady '+isChannelReady);
            //if (!isStarted && typeof localStream != 'undefined' && isChannelReady) {
            if (!isStarted &&  localStream  && isChannelReady) {
                createPeerConnection();
                isStarted = true;
                if (isInitiator) {
                    doCall();
                }	
            }
        }
        function createPeerConnection() {
            try {
                pc = new RTCPeerConnection(pc_config, pc_constraints);
                console.log("Calling pc.addStream(localStream)! Initiator: " + isInitiator);
                pc.addStream(localStream);
                pc.onicecandidate = handleIceCandidate;
                console.log('Created RTCPeerConnnection with:\n' +
                        ' config: \'' + JSON.stringify(pc_config) + '\';\n' +
                        ' constraints: \'' + JSON.stringify(pc_constraints) + '\'.');
            } catch (e) {
                console.log('Failed to create PeerConnection, exception: ' + e.message);
                alert('Cannot create RTCPeerConnection object.');
                return;
            }
            pc.onaddstream = handleRemoteStreamAdded;
            pc.onremovestream = handleRemoteStreamRemoved;
            if (isInitiator) {
                try {
                    // Create a reliable data channel
                    var label = Math.random(); 
                    //            sendChannel = pc.createDataChannel("sendDataChannel",
                    sendChannel = pc.createDataChannel(label,
                    {reliable: true});
                    trace('Created send data channel');
                } catch (e) {
                    alert('Failed to create data channel. ');
                    trace('createDataChannel() failed with exception: ' + e.message);
                }
                sendChannel.onopen = handleSendChannelStateChange;
                sendChannel.onclose = handleSendChannelStateChange;
            } else { // Joiner
                pc.ondatachannel = gotReceiveChannel;
            }
        }
        
        
        function handleIceCandidate(event) {
            console.log('handleIceCandidate event: ', event);
            if (event.candidate) {
                sendMessage({
                    type: 'candidate',
                    label: event.candidate.sdpMLineIndex,
                    id: event.candidate.sdpMid,
                    candidate: event.candidate.candidate});
            } else {
                console.log('End of candidates.');
            }
        }
        
        function sendMessage(message){
            console.log('Sending message: ', message);
            //Genera un evento message e passa l'elemento message
            mySocket.emit('message', message,mycall);
        }
        
        // Create Offer
        function doCall() {
            console.log('Creating Offer...');
            pc.createOffer(setLocalAndSendMessage, onSignalingError, sdpConstraints);
        }
        function onSignalingError(error) {
            console.log('Failed to create signaling message : ' + error.name);
        }
        // Create Answer
        function doAnswer() {
            console.log('Sending answer to peer.');
            pc.createAnswer(setLocalAndSendMessage, onSignalingError, sdpConstraints);
        }
        // Success handler for both createOffer()
        // and createAnswer()
        function setLocalAndSendMessage(sessionDescription) {
            pc.setLocalDescription(sessionDescription);
            sendMessage(sessionDescription);
        }
        /////////////////////////////////////////////////////////
        // Remote stream handlers...
        function handleRemoteStreamAdded(event) {
            console.log('Remote stream added.');
            attachMediaStream(document.querySelector('#remoteVideo'), event.stream);
            console.log('Remote stream attached!!.');
            remoteStream = event.stream;
        }
        function handleRemoteStreamRemoved(event) {
            localStream = '';
            console.log('Remote stream removed. Event: ', event);
        }
        
        // Handlers...
        function gotReceiveChannel(event) {
            trace('Receive Channel Callback');
            receiveChannel = event.channel;
            receiveChannel.onopen = handleReceiveChannelStateChange;
            receiveChannel.onmessage = onmessageHdl;
            receiveChannel.onclose = handleReceiveChannelStateChange;
        }
        function handleMessage(event) {
            trace('Received message: ' + event.data);
            var receiveTextarea=document.querySelector('#dataChannelReceive');
            receiveTextarea.value += event.data + '\n';
        }
        function handleSendChannelStateChange() {
            var readyState = sendChannel.readyState;
            trace('Send channel state is: ' + readyState);
            // If channel ready, enable user's input
            if (readyState === "open") 
            {
                alert ('send channel pronto');
            }
        }
        function handleReceiveChannelStateChange() {
            var readyState = receiveChannel.readyState;
            trace('Receive channel state is: ' + readyState);
            // If channel ready, enable user's input
            if (readyState === "open")
            {
                alert('receive channel pronto');
            } 
        }
        
    };
    
    
};

