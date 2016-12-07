var app = angular.module('ConsultarApp', ['UtilesApp', 'ngNotify']);


app.controller('ConsultarController', ['$http', '$window', 'sessionService', 'ngNotify', function($http, $window, sessionService, ngNotify) {
	var scope = this;
	scope.idPregunta = $window.location.href.match(/pregunta=([(-1)-9]+)/)[1];

	scope.init = function () {
		scope.session = sessionService.getSession();
		sessionService.checkLogin(scope.session);
		scope.motivo = null;
		scope.idTema = null;

		if (scope.idPregunta != -1) {
			$http({
					url: baseUrl + "/preguntas/" + scope.idPregunta,
					method: "GET",
					data: null,
					headers: {
						'Authorization': scope.session.token_key
					}
				}).success(function (data) {
					console.log(data);
					scope.motivo = "[" + data.pregunta.tema.tema + " - " + data.pregunta.titulo + "]";
					scope.idTema = data.pregunta.tema.id;
				}).error(function (status) {
					
				});
		}
	}
	
	scope.sendDuda = function(){
            if (!(scope.motivo && scope.duda)) {
                ngNotify.set('Ingresa los campos', 'warn');
            } else if (scope.motivo.length > 255) {
                ngNotify.set('El motivo es demasiado largo.', 'warn');
            } else if (scope.duda.length > 255) {
                ngNotify.set('La consulta es demasiado larga.', 'warn');
            } else {
                var datos = {};
                datos.duda = scope.duda;
                datos.motivo = scope.motivo;
                $http({
                    url: baseUrl + "/dudas",
                    method: "POST",
                    data: datos,
                    headers: {
                        'Authorization': scope.session.token_key
                    }
                }).success(function (data) {
                    ngNotify.set('Consulta enviada!  :)', 'success', scope.redirect);
                }).error(function (status) {
                    ngNotify.set('Error al enviar la consulta', 'error');
                });
            }
	}

	scope.redirect = function() {
		if (scope.idTema == null) {
			$window.location.href = 'listaconsultas.html?';
		} else {
			$window.location.href = 'preguntas.html?id=' + scope.idTema;
		}
		
	}

}]);


