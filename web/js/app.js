'use strict';
//variabili globali

var baseURL = 'http://localhost:8084/nice2mit_backend/restAPI/';

//window.alert = function() {};

// Declare app level module which depends on filters, and services
angular.module('nice2mitAPP', [
    'ngRoute',
    'nice2mitAPP.filters',
    'nice2mitAPP.services2',
    'nice2mitAPP.directives',
    'nice2mitAPP.controllers2',
    'nice2mitAPP.chat',
    'nice2mitAPP.webrtc_connectors',
    'nice2mitAPP.presence',
    'ngModal'
]).
    config(['$routeProvider', '$locationProvider' , function($routeProvider , $locationProvider)
    {
        //$locationProvider.html5Mode(true);
        
        $routeProvider.when('/', {templateUrl: 'partials/main.html' , controller: 'mainCtrl' , public : true});
        $routeProvider.when('/profile', {templateUrl: 'partials/profile.html', controller: 'profileCtrl' , public : false});
        $routeProvider.when('/about', {templateUrl: 'partials/about.html' , public : true});
        $routeProvider.when('/users/:username', {templateUrl: 'partials/users.html', controller: 'usersCtrl' , public : false});
        
        $routeProvider.otherwise({redirectTo: '/'});
    }]);