var app = angular.module('ListaConsultasApp', ['UtilesApp']);


app.controller('ListaConsultasController', ['$scope', '$http', '$window', 'sessionService', '$timeout', function($scope, $http, $window, sessionService, $timeout) {
	var scope = this;
	scope.consultas = null;


	scope.init = function() {
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);
	 	$http({
	        url: baseUrl + "/dudas/",
	        method: "GET",
	         headers: {
	 		 'authorization': scope.session.token_key
	 		 }
	    }).success(function (data) {
	    	scope.consultas = data;
	    	console.log(scope.consultas);
	    }).error(function (status) {
	        console.log("ERROR: No se pudo traer las consultas");
	    });
	}

	 scope.accederConsulta = function (consulta) {
	 	$window.location.href = 'verconsulta.html?id=' + consulta.id;
	 }

	 
}]);
