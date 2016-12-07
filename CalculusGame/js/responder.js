var app = angular.module('ResponderApp', ['UtilesApp', 'ngNotify']);

function onYouTubeIframeAPIReady() {
    app.player = new YT.Player('player');
//    angular.element($('#wrapper')).scope().player=new YT.Player('player');
};

app.config(function($sceDelegateProvider) {
  $sceDelegateProvider.resourceUrlWhitelist([
    'self',
    'https://www.youtube.com/**'
  ]);
});

app.controller('ResponderController', ['$scope', '$http', '$window', 'sessionService', 'ngNotify', function($scope, $http, $window, sessionService, ngNotify, $sceDelegateProvider) {
	var scope = this;
	scope.pregunta = null;
	scope.respuesta = null;
	scope.showTip = false;
	scope.esFormula = false;
	scope.idPregunta = $window.location.href.match(/id=([0-9]+)/)[1];

	scope.hideTip = function() {
            scope.showTip = false;
            app.player.stopVideo();
        };

	scope.init = function() {
		//Get session and check if logged in
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);

		$http({
	          url: baseUrl + "/preguntas/" + scope.idPregunta,
	          method: "GET",
	          headers: {
			 'Authorization': scope.session.token_key
			 }
	   }).success(function (data) {
	       scope.pregunta = data.pregunta;
	   }).error(function (status) {
	       console.log("ERROR: No se pudo traer los temas");
	   });
	}

  	scope.verificarRespuesta = function() {
  		if (scope.respuesta != null) {
  			var datos = {r: scope.respuesta};
			$http({
				url: baseUrl + "/preguntas/" + scope.idPregunta + "/respuesta",
				method: "PUT",
				params: datos,
				headers: {
					'Authorization': scope.session.token_key
				}
				}).success(function (data) {
			   		scope.procesarRespuesta(data);
				}).error(function (status) {
					ngNotify.set('No se pudo traer la respuesta', 'error');
		   			console.log("ERROR: No se pudo traer los resupesta");
			});
  		} else {
  			ngNotify.set('No ingresaste una respuesta!', 'warn');
  		}
	}
  	
  	scope.procesarRespuesta = function(respuestaServ) {
  		if (respuestaServ.correcto){
  			scope.session.puntaje = scope.session.puntaje + scope.pregunta.puntos;
	 		$window.localStorage['session'] = angular.toJson(scope.session);
			ngNotify.set('Correcto!', {type:'success', duration: 1000}, scope.redirect);

  		}
  		else {
			ngNotify.set('Respuesta incorrecta :(', 'error');
  			scope.showTip = true; 
  		}
  	}

  	scope.redirect = function () 
	{
		$window.location.href = 'preguntas.html?id=' + scope.pregunta.tema.id
	}

  	scope.goConsultar = function(){
  		$window.location.href =  'consultar.html?pregunta=' + scope.pregunta.id;
  	}

}]);
