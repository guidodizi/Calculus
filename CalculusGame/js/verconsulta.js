var app = angular.module('VerConsultaApp', ['UtilesApp']);



app.controller('VerConsultaController', ['$scope', '$http', '$window', 'sessionService', function($scope, $http, $window, sessionService) {
	var scope = this;
	scope.consulta = null;
	scope.idConsulta = $window.location.href.match(/id=([0-9]+)/)[1];


	scope.init = function() {
		//Get session and check if logged in
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);

		$http({
	          url: baseUrl + "/dudas/" + scope.idConsulta,
	          method: "GET",
	          headers: {
			 'Authorization': scope.session.token_key
			 }
	   }).success(function (data) {
	       scope.consulta = data;
	       console.log(scope.consulta);
	   }).error(function (status) {
	       console.log("ERROR: No se pudo traer los temas");
	   });
	}

}]);
