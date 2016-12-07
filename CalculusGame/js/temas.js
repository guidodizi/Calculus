var app = angular.module('TemasApp', ['UtilesApp']);

// app.config(function ( $httpProvider) {     
// 	$httpProvider.defaults.headers.common['Access-Control-Allow-Origin'] = '*'; 
// 	// $httpProvider.defaults.headers.common.Accept = $httpProvider.defaults.headers.common.Accept + ', authorization';
// 	var b =3;
// })


app.controller('TemasController', ['$scope', '$http', '$window', 'sessionService', function($scope, $http, $window, sessionService) {
	var scope = this;
	scope.temas = null;

	scope.init = function() {
		//Get session and check if logged in
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);

		$http({
	          url: baseUrl + "/temas",
	          method: "GET",
	          headers: {
			 'Authorization': scope.session.token_key
			 }
	   }).success(function (data) {
	       scope.temas = data;
	   }).error(function (status) {
	       console.log("ERROR: No se pudo traer los temas");
	   });
	}

	scope.accederTema = function (tema) {
		$window.location.href =  'preguntas.html?id=' + tema.id;
	}

}]);
