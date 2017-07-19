/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

angular.module('nice2mitAPP.chat', ['nice2mitAPP.services2' , 'nice2mitAPP.webrtc_connectors' , 'emoji' , 'ngSanitize']).
        controller('ChatCtrl' , ChatCtrl).
        controller('videoChatCtrl' , videoChatCtrl);

function ChatCtrl($scope, $routeParams , userService , VideoService)
{
    
    $scope.isInitiator = true;
    
    userService.getUser().then(function(userData){$scope.sender = userData.username;}); 
    $scope.receiver = $routeParams.username;
    
    
    $scope.messaggi=new Array(); 
    
    
    //invio messaggi
    $scope.send=function()
    {
        VideoService.sendData($scope.myMsg);

        $scope.messaggi.push({"testo": $scope.sender + "<br>" + $scope.myMsg , "ricevuto" : false});
        positioning();
   

    };
    
    
    var aaa = function(event)
    {
        $scope.messaggi.push({"testo": $scope.receiver + "<br>" + event.data , "ricevuto" : true});
        $scope.isInitiator = false;
    };
    
    //ricezione messaggi
    VideoService.onmessage(aaa);
    
    $scope.myFunct = function(keyEvent) {
        if (keyEvent.which === 13)
            $scope.send();
    };

    
    var positioning=function(){
        var objDiv = document.getElementById("chatbox");
        objDiv.scrollTop = objDiv.scrollHeight;
        $scope.myMsg='';
        
    };
   
};


function videoChatCtrl ($scope , VideoService , $routeParams)
{
    VideoService.initVideo(function(){$scope.videochatModalShow = true;});

    
    $scope.videochat = function ()
    {
        $scope.videochatModalShow = true;

        VideoService.call($routeParams.username);        
    };
    
    $scope.closeVideochatDialog  = function ()
    {
        VideoService.hangup();
        $scope.videochatModalShow = false;
    };
}