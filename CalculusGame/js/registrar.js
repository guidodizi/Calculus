var app = angular.module('RegistrarApp', ['UtilesApp', 'ngNotify']);

app.controller('RegistrarController', ['$http', '$window', 'ngNotify', function($http, $window, ngNotify) {
  var scope = this;
  var emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  scope.sendRegistrar = function() {
      
    if (!scope.name){
       ngNotify.set('No ingresaste tu nombre ¯\\_(ツ)_/¯', 'warn');
    }
    else if (!scope.email){
       ngNotify.set('No ingresaste tu email ¯\\_(ツ)_/¯', 'warn');
    }
    else if (!scope.password){
       ngNotify.set('No ingresaste una contraseña ¯\\_(ツ)_/¯', 'warn');
    }
    else if (!emailPattern.test(scope.email)) {
       ngNotify.set('No ingresaste un email válido ¯\\_(ツ)_/¯', 'warn');
    }
    else if (scope.name.length > 255){
       ngNotify.set('El nombre es demasiado largo ¯\\_(ツ)_/¯', 'warn');
    }
    else if (scope.email.length > 255){
       ngNotify.set('El email es demasiado largo ¯\\_(ツ)_/¯', 'warn');
    }
    else if (scope.password.length > 255){
       ngNotify.set('La contraseña es demasiado larga ¯\\_(ツ)_/¯', 'warn');
    }
    else{
      var datos = {email: scope.email, password: scope.password, name: scope.name};
      $http({
             url: baseUrl + "/session/registrar",
             method: "POST",
             params: datos,
         }).success(function (data) {
             scope.registroCorrecto(data);
         }).error(function (status) {
             console.log("ERROR: Llamando a registrarse");
             ngNotify.set('Ese mail ya existe ¯\\_(ツ)_/¯', 'error');
         });
    }
  	
  }

  scope.access = function (e) {
    if (e.keyCode == 13)
      scope.sendRegistrar();
  }

  scope.registroCorrecto = function (data){
  	$window.localStorage['session'] = angular.toJson(data);
  	$window.location.href =  'temas.html';	
  }


}]);


