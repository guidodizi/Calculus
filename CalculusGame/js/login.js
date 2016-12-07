var app = angular.module('LoginApp', ['UtilesApp', 'ngNotify']);

app.controller('LoginController', ['$http', '$window', 'ngNotify', function($http, $window, ngNotify) {
  var scope = this;
  scope.errorLog = false;

  scope.sendLogin = function() {
    if (scope.email && scope.password){
      var datos = {email: scope.email, password: scope.password};
      $http({
             url: baseUrl + "/session/login/alumno",
             method: "GET",
             params: datos,
         }).success(function (data) {
             scope.logeoCorrecto(data);
         }).error(function (status) {
             console.log("ERROR: Llamando al loguearse");
             ngNotify.set('Usuario y/o contraseña incorrecta', 'error');
         });
      }
    else{
       ngNotify.set('No ingresaste datos ¯\\_(ツ)_/¯', 'warn');
    }
  	
  }

  scope.access = function (e) {
    if (e.keyCode == 13)
      scope.sendLogin();
  }

  scope.logeoCorrecto = function (data){
  	$window.localStorage['session'] = angular.toJson(data);
  	$window.location.href =  'temas.html';	
  }

  scope.fbLogin = function(){
  FB.login(function(response) {

      if (response.authResponse) {
          console.log('inicie con fb.');
          console.log(response);
          access_token = response.authResponse.accessToken; //get access token
          user_id = response.authResponse.userID;

          FB.api('/me', function(response) {
            var datos = {fbid: response.id, name: response.name};
            $http({
                 url: baseUrl + "/session/fblogin/alumno",
                 method: "GET",
                 params: datos,
             }).success(function (data) {
                 scope.logeoCorrecto(data);
             }).error(function (status) {
                 console.log("ERROR: Llamando al loguearse fb-facebook");
                 ngNotify.set('Usuario y/o contraseña incorrecta', 'error');
             });          
          });

      } else {
          console.log('error login fb.');
      }
  }, {
      scope: 'public_profile,email'
  });
}


}]);


// app.service('sessionService', [ '$window', function ($window) {

//   this.getSession = function (){
//     var sessionJson = $window.localStorage['session'];
//     var session = angular.fromJson(sessionJson);
//     return session;
//   }
// }])


//Login con FB by Tavi -- Si esta muy mal perdon, no se usar angular
window.fbAsyncInit = function() {
  FB.init({
    appId      : fbapp,
    cookie     : true,
    xfbml      : true,
    version    : 'v2.5' //puede ser 2.8 tmb creo
  });
};

(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));




