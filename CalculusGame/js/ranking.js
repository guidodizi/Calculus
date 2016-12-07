var app = angular.module('RankingApp', ['UtilesApp']);

app.controller('RankingController', ['$scope', '$http', '$window', 'sessionService', '$timeout', function($scope, $http, $window, sessionService, $timeout) {
	var scope = this;
	scope.ranking = null;

	scope.init = function() {
		//Get session and check if logged in
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);
	 	$http({
	        url: baseUrl + "/alumnos/ranking" ,
	        method: "GET",
	         headers: {
	 		 'authorization': scope.session.token_key
	 		 }
	    }).success(function (data) {
	    	scope.ranking = data;
	    }).error(function (status) {
	        console.log("ERROR: No se pudo traer el ranking");
	    });
	}

}]);
