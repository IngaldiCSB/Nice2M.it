'use strict';

/* Directives */


angular.module('nice2mitAPP.directives', []).
  directive('appVersion', ['version', function(version) {
    return function(scope, elm, attrs) {
      elm.text(version);
    };
  }]);
