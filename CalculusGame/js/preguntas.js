var app = angular.module('PreguntasApp', ['UtilesApp', 'ngNotify']);


app.controller('PreguntasController', ['$scope', '$http', '$window', 'sessionService', '$timeout', 'ngNotify', function($scope, $http, $window, sessionService, $timeout, ngNotify) {
	var scope = this;
	scope.ordenIds = [];
	scope.preguntas = null;
	scope.idTema = $window.location.href.match(/id=([0-9]+)/)[1];


	scope.init = function() {
		//Get session and check if logged in
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);
	 	$http({
	        url: baseUrl + "/temas/"+scope.idTema+"/preguntas/" ,
	        method: "GET",
	         headers: {
	 		 'authorization': scope.session.token_key
	 		 }
	    }).success(function (data) {
	        scope.functionSuccessPreguntas(data);
	    }).error(function (status) {
	        console.log("ERROR: No se pudo traer los temas");
	    });
	}

	 scope.functionSuccessPreguntas = function(data){
	 	scope.preguntas = data.sort(scope.ordenarPreguntas);
		for (index = 0; index < scope.preguntas.length; ++index) {
			scope.ordenIds[index] = scope.preguntas[index].pregunta.id;
        }
	 	scope.tema = scope.preguntas[0].pregunta.tema
	 	scope.porcentajeTema();
	 }

	 scope.porcentajeTema = function() {
	 	var blocked = 0;
	 	scope.correct = 0;
	 	var available = 0;
	 	scope.total = 0;
	 	scope.preguntas.forEach(function (p) {
	 		if(p.blocked)
	 			blocked++;
	 		else{
	 			if (!p.respuestaCorrecta)
	 				available++;
	 			else if (p.respuestaCorrecta)
	 				scope.correct++;
	 		}
	 		scope.total++;
	 	})
	 	scope.completed = (scope.correct * 100) / scope.total;
	 	scope.unlocked = ((scope.correct + available) * 100) / scope.total;
	 }

	 scope.ordenarPreguntas = function (a,b){
	 	// pregunta ya respondida va para el fondo
	 	if (!a.blocked && a.respuestaCorrecta && !b.blocked && b.respuestaCorrecta) {
	 		if(a.pregunta.id <= b.pregunta.id)
	 			return -1;
		 	if(a.pregunta.id > b.pregunta.id)
		 		return 1;
	 	}
	 	if (!b.blocked && b.respuestaCorrecta)
	 		return -1;
	 	if (!a.blocked && a.respuestaCorrecta)
	 		return 1;
	 	// despues las bloqueadas
	 	if (b.blocked)
	 		return -1;
	 	if (a.blocked)
	 		return 1;
	 	if(a.pregunta.id <= b.pregunta.id)
	 		return -1;
	 	if(a.pregunta.id > b.pregunta.id)
	 		return 1;
	 	return 0;

	 }
	 scope.accederPregunta = function (pregunta) {
	 	$window.location.href = 'responder.html?id=' + pregunta.pregunta.id;
	 }
	 
	 scope.mostrarAyuda = function (pregunta) {
		$http({
	        url: baseUrl + "/preguntas/" + pregunta.pregunta.id,
	        method: "GET",
	         headers: {
	 		 'authorization': scope.session.token_key
	 		 }
	    }).success(function (data) {

	        scope.functionSuccessAyuda(data.pregunta.preguntasPrevias);
	    }).error(function (status) {
	        console.log("ERROR: No se pudo obtener Ayuda");
	    });
	 }
	 
	 scope.functionSuccessAyuda = function(data){
		var msj = 'Para desbloquear esta pregunta debe responder previamente alguna de las siguientes: ';
		var previas = [];
		for (index = 0; index < data.length; ++index) {

			previas[index] = scope.ordenIds.indexOf(data[index])+1;
        }
		msj += previas.join();
	 	ngNotify.set(msj, 'warn');
	 }

	 
}]);
