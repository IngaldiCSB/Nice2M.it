'use strict';

/* Controllers */

angular.module('nice2mitAPP.controllers2', ['nice2mitAPP.services2' , 'nice2mitAPP.presence']).
        
controller('mainCtrl', ['$scope' , '$location' , 'sessionService' , 'PresenceService' , mainCtrl]).
controller('profileCtrl', ['$scope' ,'$http', '$q' , '$location' , 'profileService','userService' ,'friendsService', profileCtrl]).
controller('usersCtrl', usersCtrl).
controller('headerCtrl', ['$scope' ,'$http','$location' ,  'sessionService' , 'userService', 'profileService' , 'friendsService' , headerCtrl]).
controller('loginDialogCtrl', loginDialogCtrl).
controller('signupDialogCtrl', signupDialogCtrl);

//----------------------------------------------------------------------------

//mainCtrl: controller principale, qui tutte le cose globali
function mainCtrl ($scope , $location , sessionService , PresenceService)
{
    //è in ascolto sui cambiamenti dell'url e verifica se la route richiesta è 
    //accessibile agli utenti non loggati, altrimenti redirecta sulla home
    $scope.$on('$routeChangeStart', function (e, next, current)
    {               
        if (next.public !== undefined && !next.public && !sessionService.isLogged())
        {
            alert("Utente non loggato!!");
            $location.path("/");                   
        }
    });
    

    
}

function profileCtrl ($scope ,$http, $q ,  $location , profileService, PresenceService , friendsService)
{


        profileService.getProfile().then(function(data)
        {
            $scope.profile = data;
            
            return friendsService.getFriendList();
          
        }).then(function(data)
        {
           $scope.amici = data; 
        });

        
        $scope.amicoURL = function (nome)
        {
            return "#/users/" + nome;
        };
        
        $scope.search=function(Search){
          $scope.addSearch=true;
          var searchURL = "restAPI/users/"+Search.username;
          $http.get(searchURL).
          success(function(data, status, headers, config) {
                if(Search.username===data.username){
                    $scope.result=true;  
                    $scope.ricercato=data.username;
                }else{
                  $scope.result=false;  
                  $scope.ricercato="Utente non esistente";
              }
              
          }).
         error(function(data, status, headers, config) {
            alert("Ripetere ricerca...");
        });       
        
    };
    
}

function usersCtrl ($scope , $routeParams , $http , friendsService , PresenceService)
{


    var profileURL = "restAPI/users/" + $routeParams.username + "/profile";
    $http.get(profileURL).
          success(function(data, status, headers, config) {
             $scope.nome=data.nome;
             $scope.cognome=data.cognome;
             $scope.about=data.about;
             $scope.immagine = data.avatar;
         }).
         error(function(data, status, headers, config) {
               alert("PROFILO NON CARICATO");
        });
        
         var friendURL = "restAPI/users/"+ $routeParams.username+ "/amici";
         

      $http.get(friendURL).
          success(function(data, status, headers, config) {

              $scope.amici=data.amici;
         }).
         error(function(data, status, headers, config) {
               alert("AMICI NON CARICATI");
        });
        
        
        $scope.amicoURL = function (nome)
        {
            return "#/users/" + nome;
        };

    
    $scope.addFriend=function()
    {
        PresenceService.addFriend($routeParams.username);
    };
    
    $scope.username = $routeParams.username;
    $scope.isFriend = friendsService.isFriend($scope.username);
}

function headerCtrl ($scope ,$http, $location , sessionService , userService , profileService , friendsService)
{
    $scope.myURL = function ()
    {
        return "#/profile";
    };
    
    $scope.openLoginDialog = function ()
    {
        $scope.modalShow = true;
    };
    
    $scope.doLogout = function ()
    {
        //resetto il session service
        sessionService.resetSessionStatus ();
        userService.setUser(null);
        profileService.setProfile(null);
        friendsService.setFriendList(null);
        $scope.isLogged = sessionService.isLogged();
        
        $location.path('/main');

    };
    
    $scope.$on ('login_event' , function (event, args)
    {

       
        
        userService.getUser().then(function(userData)
        {
            $scope.isLogged = true;
            $scope.user = userData;
            
                    
            //se il modal è aperto, lo chiudo
            $scope.modalShow= false;
            
            
            //e passo alla pagina profilo
            $location.path('/profile');

        });

        



    });
    
    $scope.$on('login_diag_event' , function (event, args)
    {
        $scope.modalShow = args;

    });
    
    

}

function loginDialogCtrl ($scope , sessionService , PresenceService , userService)
{
    
    $scope.closeLogInDialog = function()
    {
        $scope.$emit('login_diag_event' ,false);
    };
    
    $scope.sendLoginReq = function (auth)
    {

        //invio richiesta al server con dati di autenticazione
        
        
        //se tutto va a buon fine, mi ritorna un 
        //oggetto che rappresenta la sessione creata. l'oggetto sessione viene
        //mantenuto dal sessionService


        //{'email' : 'ciro@lota.it' , 'password' : 'ciro'}
        sessionService.newSession(auth).then(function()
        {

          
           userService.getUser().then(function(userData){
               
               $scope.mammt = userData;
          
                PresenceService.login($scope.mammt.username).then(function ()
                {
                     $scope.$emit('login_event' , sessionService.isLogged());
              
                 });
            });
                          //$scope.$emit('login_event' , sessionService.isLogged());
           
            //alert('loggato?' + sessionService.isLogged().toString());
        });
        


    };  
    
    $scope.openSignupDialog = function ()
    {
        
        $scope.signupModalShow = true;

    };
    
    $scope.$on ('signup_event' , function (event, args)
    {
        $scope.signupModalShow = false;
        
    });
   
}

function signupDialogCtrl ($scope,$http)
{
    var imgname = '';
    
    $scope.add = function()
    
    {

        var f = document.getElementById('image').files[0], r = new FileReader(); r.onloadend = function(e){ var data = e.target.result; var req = { method: 'POST', url: 'http://localhost:8084/nice2mit_backend/restAPI/images/img', headers: { 'Content-Type': "image/jpeg" }, data: data };
    
        $http(req).success(function(data){;}).error(function(data){;}); }; r.readAsDataURL(f);
    };
 
    
    
    $scope.closeSignupDialog = function ()
    {
        $scope.$emit('signup_event' , false);       
    };
    
    $scope.sendSignupReq = function (reg)
    {
    
        var user =
        {
                    "nome" : reg.first_name,
                    "cognome"  : reg.last_name,
                    "about"  : reg.about,
                    "credenziali":
                    {
                        "email"  : reg.email,
                        "password"  : reg.password
                    },
                    "avatar" : ''
         };
    
    if (reg.password!== reg.password_confirmation){
        alert("Le password NON coincidono");
    }else{
       $http.post("restAPI/users/"+ reg.username.toString(), user).
          success(function(data, status, headers, config) {
         }).
         error(function(data, status, headers, config) {
               alert("errore registrazione");
        });     
     
     }
            // e chiudo il modal
            $scope.closeSignupDialog ();
    };
}
//----------------------------------------------------------------------------


//-------------------------FUNZIONI GLOBALI-------------------------------------
