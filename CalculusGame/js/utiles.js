
//var baseUrl = "https://pis2016.azurewebsites.net/CalculusBackend/v2/game"; 	//PRODUCCION
//var baseUrl = "http://ec2-52-42-2-241.us-west-2.compute.amazonaws.com:8080/CalculusBackendProd/v2/game";     //PRODUCCION 2
//var baseUrl = "http://pis2016beta.azurewebsites.net/CalculusBackend/v2/game";     //TESTING
var baseUrl = "http://localhost:8080/CalculusBackend/v2/game"; 				//LOCALHOST
//var baseUrl = "http://192.168.1.6:8084/CalculusBackend/v2/game";              //LOCALHOST
//var baseUrl = "http://mvpcasa.dynu.com:1234/CalculusBackend/v2/game";         //SERVER MARTIN
//var baseUrl = "http://abentan.ddns.net:8085/CalculusBackend/v2/game"; 		//SERVER ALEJANDRO
//var baseUrl = "https://calculus2016.herokuapp.com/v2/game";     //TESTING2
//var baseUrl = "http://ec2-52-42-2-241.us-west-2.compute.amazonaws.com:8080/CalculusBackend/v2/game";     //TESTING3

var fbapp = '515521808648757'; //TESTING (pis2016beta fb app)
//var fbapp = '515521808648757'; //PRODUCCION (pis2016 fb app)

var app = angular.module('UtilesApp', []);

app.controller('headerController', ['$window', function($window) {
  var scope = this;

  scope.logout = function() {
    localStorage.removeItem('session');
    $window.location.href =  'login.html';  
  }

}]);

//DIRECTIVES
app.directive('headerDirective', function() {
  return {
    restrict: 'E',
    scope: {
        temaActual: '='
    },
    controller: 'headerController',
    controllerAs: 'headerCtrl',
    
    template: '<header id="header">' +
        '<div class="navbar navbar-inverse" role="banner">' +
            '<div class="container"> ' +
                '<div class="navbar-header"> ' +
                    '<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> ' +
                        '<span class="sr-only">Toggle navigation</span> ' +
                        '<span class="icon-bar"></span> ' +
                        '<span class="icon-bar"></span> ' +
                        '<span class="icon-bar"></span> ' +
                    '</button>' +

                    '<a class="navbar-brand" href="temas.html">' +
                    	'<h1><img src="images/Layer1.png" alt="logo" style="width: 200px; max-width: 90%;"></h1>' +
                    '</a>' +
                    
                '</div>' +


                '<div class="collapse navbar-collapse">' +
                    '<ul class="nav navbar-nav navbar-right">' + 
                        '<li><a href="temas.html">Temas</a></li>' + 
                        '<li><a ng-show="temaActual != undefined" href="preguntas.html?id={{temaActual.id}}">{{temaActual.tema}}</a></li>' +
                        '<li ng-show="headerCtrl.temaActual != undefined"><a href="preguntas.html">Temas</a></li>' + 
                        '<li><a href="ranking.html">Ranking</a></li>' +  
                        '<li><a href="listaconsultas.html">Consultas</a></li>' +                   
                        '<li><a href="#" ng-click="headerCtrl.logout()">Salir</a></li>' +                    
                    '</ul>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</header>'
 
      };
});

app.directive('footerDirective', function() {
  return {
    restrict: 'E',
    controller: 'headerController',
    controllerAs: 'headerCtrl',
    
    template:   
        '<footer id="footer">' +
            '<div class="container">' +
                '<div class="row">' +
                    '<div class="col-sm-12 text-center bottom-separator">' +
                        '<img src="images/home/under.png" class="img-responsive inline" alt="">' +
                    '</div>' +
                '<div class="col-sm-12">' +
                    '<div class="copyright-text text-center">' +
                        '<p style="color: black;">© Calculus 2016</p>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>' +
    '</footer>'
    };
});

app.directive('puntajeDirective', function() {
  return {
    restrict: 'E',
    scope: {
        puntaje: '='
    },
    template:       '<div ng-cloak class="score">' +
                        '{{this.puntaje}}' +
                    '</div>' 
  };
});

// SERVICES
app.service('sessionService', [ '$window', function ($window) {

  this.getSession = function (){
    var sessionJson = $window.localStorage['session'];
    var session = angular.fromJson(sessionJson);
    return session;
  }

  this.checkLogin = function (session){
    if (session == null || session == undefined){
        window.location.href =  'login.html'
    }
  }


}])
