'use strict';

/* Controllers */

angular.module('nice2mitAPP.controllers', ['nice2mitAPP.services']).
        
controller('mainCtrl', ['$scope' , '$location' , 'sessionService' , mainCtrl]).
controller('profileCtrl', ['$scope' ,'$http','$location' , 'profileService','userService' ,'friendsService', profileCtrl]).
controller('usersCtrl', ['$scope' , '$routeParams' , '$http', 'friendsService' , usersCtrl]).
controller('headerCtrl', ['$scope' ,'$http','$location' ,  'sessionService' , 'userService', 'profileService' , headerCtrl]).
controller('loginDialogCtrl', ['$scope' , '$location' , '$http' , 'sessionService' , loginDialogCtrl]).
controller('signupDialogCtrl', ['$scope' ,'$http', signupDialogCtrl]);

//----------------------------------------------------------------------------

//mainCtrl: controller principale, qui tutte le cose globali
function mainCtrl ($scope , $location , sessionService)
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

function profileCtrl ($scope ,$http, $location , profileService,userService,friendsService)
{
    alert ('profileCtrl')
    if (profileService.getProfile() !== null)
    {
                alert('NON primo caricamento');

            $scope.nome=profileService.getProfile().nome;
            $scope.cognome=profileService.getProfile().cognome;
            $scope.about=profileService.getProfile().about;
            $scope.immagine=profileService.getProfile().avatar;         
    }
    else
    {
        alert('primo caricamento ' + userService.getUser());
        var profileURL = userService.getUser().profilo;
        $http.get(profileURL).
                success(function(data, status, headers, config) {
            $scope.nome=data.nome;
            $scope.cognome=data.cognome;
            $scope.about=data.about;
            $scope.immagine=data.avatar;
            
            profileService.setProfile(data);
            
        }).
                error(function(data, status, headers, config) {
            alert("PROFILO NON CARICATO");
        });      
        
    }
        
        var friendURL = userService.getUser().amici;
        $http.get(friendURL).
                success(function(data, status, headers, config) {
            $scope.amici=data.amici;
            friendsService.setFriendList(data.amici);
        }).
                error(function(data, status, headers, config) {
            alert("AMICI NON CARICATI");
        });
        
    
        
        $scope.amicoURL = function (nome)
        {
            return "#/users/" + nome;
        };
        
        $scope.search=function(Search){
          $scope.addSearch=true;
          var searchURL = "http://localhost:8084/nice2mit_backend/restAPI/users/"+Search.username;
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

function usersCtrl ($scope , $routeParams , $http , friendsService)
{
    var profileURL = "http://localhost:8084/nice2mit_backend/restAPI/users/" + $routeParams.username + "/profile";
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
        
         var friendURL = "http://localhost:8084/nice2mit_backend/restAPI/users/"+ $routeParams.username+ "/amici";
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
    
    
    $scope.videochat=function(){
        
    };
    
    $scope.addFriend=function(){
        
    };
    
    $scope.username = $routeParams.username;
    $scope.isFriend = friendsService.isFriend($scope.username);
}

function headerCtrl ($scope ,$http, $location , sessionService , userService , profileService)
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
        $scope.isLogged = sessionService.isLogged();
        
        $location.path('/main');

    };
    
    $scope.$on ('login_event' , function (event, args)
    {

        $scope.isLogged = args;
        
        //CARICO UTENTE
          $http.get(sessionService.getSession().utente).
          success(function(data, status, headers, config) {
              $scope.username = data.username;
              userService.setUser(data);
              
                      
                //se il modal è aperto, lo chiudo
                $scope.modalShow= false;
              
         }).
         error(function(data, status, headers, config) {
               alert("ERROR");
        });
        

        


    });
    
    $scope.$on('login_diag_event' , function (event, args)
    {
        $scope.modalShow = args;

    });
    
    

}

function loginDialogCtrl ($scope , $location , $http , sessionService)
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
        
        //ATTENZIONE: nell'oggetto auth ci vanno email e password, e newsession()
        //deve effettuare la richiesta post!!!!!!!!
        //var auth = {id : 111 , username : "carmine"};
        $http.post('http://localhost:8084/nice2mit_backend/restAPI/sessions', auth).
          success(function(data, status, headers, config) {
              sessionService.setSession(data);
              sessionService.setLogged(true);
              
            //notifico lo scope padre che è avvenuto l'evento 'login'              
            $scope.$emit('login_event' , sessionService.isLogged());
              
              //passo alla pagina profilo
              $location.path('/profile');


         }).
         error(function(data, status, headers, config) {
               alert("Log-in fallito");
              sessionService.setLogged(false);
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
    
    $scope.add = function(){
  var f = document.getElementById('image').files[0],
      r = new FileReader();
  r.onloadend = function(e){
    var data = e.target.result;
    
    var req = {
        method: 'POST',
        url: 'http://localhost:8084/nice2mit_backend/restAPI/images/img',
        headers: {
                'Content-Type': "image/jpeg"
                },
        data:  data
        };

        $http(req).success(function(data)
        {alert("LOTAIMMM");
         imgname = data.name;
        })
        .error(function(data){alert("LOTA2IMM");});
   
  };
   r.readAsBinaryString(f);
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
                    "avatar" : imgname
         };
    
    if (reg.password!== reg.password_confirmation){
        alert("Le password NON coincidono");
    }else{
       $http.post("http://localhost:8084/nice2mit_backend/restAPI/users/"+ reg.username.toString(), user).
          success(function(data, status, headers, config) {
              alert("LOTA");
         }).
         error(function(data, status, headers, config) {
               alert("LOTA2");
        });     
     
     }
            // e chiudo il modal
            $scope.closeSignupDialog ();
    };
}
//----------------------------------------------------------------------------


//-------------------------FUNZIONI GLOBALI-------------------------------------
