// Archivo JavaScript para CalculusAdmin


//var baseUrl = "https://pis2016.azurewebsites.net/CalculusBackend/v2/admin"; 	//PRODUCCION
//var baseUrl = "http://pis2016beta.azurewebsites.net/CalculusBackend/v2/admin"; 	//TESTING
//var baseUrl = "http://localhost:8080/CalculusBackend/v2/admin"; 				//LOCALHOST
//var baseUrl = "http://mvpcasa.dynu.com:1234/CalculusBackend/v2/admin"; 		//SERVER MARTIN
//var baseUrl = "http://abentan.ddns.net:8085/CalculusBackend/v2/admin"; 		//SERVER ALEJANDRO
//var baseUrl = "https://calculus2016.herokuapp.com/v2/admin";     //TESTING2
var baseUrl = "http://ec2-52-42-2-241.us-west-2.compute.amazonaws.com:8080/CalculusBackend/v2/admin";     //TESTING3


// ---------------------------------------------------------------------
// -----------------------   NOTIFICACIONES   --------------------------

function mostrarNotificacionExito(mensaje){
	$("#notificacion").addClass("alert alert-success alert-dismissible");
	$("#tituloNotificacion").text("¡Éxito!");
	$("#textoNotificacion").text(mensaje);
	$("#notificacion").fadeIn("slow");
	$(".dismiss").click(function(){
		$("#notificacion").fadeOut("slow");
	});
}

function mostrarNotificacionAlerta(mensaje){
	$("#notificacion").addClass("alert alert-warning alert-dismissible");
	$("#tituloNotificacion").text("¡Atención!");
	$("#textoNotificacion").text(mensaje);
	$("#notificacion").fadeIn("slow");
	$(".dismiss").click(function(){
		$("#notificacion").fadeOut("slow");
	});
}

function mostrarNotificacionError(mensaje){
	$("#notificacion").addClass("alert alert-danger alert-dismissible");
	$("#tituloNotificacion").text("¡Error!");
	$("#textoNotificacion").text(mensaje);
	$("#notificacion").fadeIn("slow");
	$(".dismiss").click(function(){
		$("#notificacion").fadeOut("slow");
	});
}

function mostrarNotificacionTema(){
	var paramExito = getURLParameter('exito');
	if (paramExito != null){
		if(paramExito == "1"){
			mostrarNotificacionExito("Tema registrado correctamente.");
		}else if(paramExito == "2"){
			mostrarNotificacionExito("Tema editado correctamente.");
		}else if(paramExito == "3"){
			mostrarNotificacionExito("Tema eliminado correctamente.");
		}
	}
	var paramError = getURLParameter('error');
	if (paramError != null){
		if(paramError == "1"){
			mostrarNotificacionError("Error al registrar tema.");
		}else if(paramError == "2"){
			mostrarNotificacionError("Error al editar tema.");
		}else if(paramError == "3"){
			mostrarNotificacionError("Error al eliminar tema.");
		}else if(paramError == "4"){
			mostrarNotificacionError("Error al cargar tema.");
		}else if(paramError == "5"){
			mostrarNotificacionError("Error al eliminar, el tema tiene preguntas asociadas.");
		}
	}	
}

function mostrarNotificacionPregunta(){
	var paramExito = getURLParameter('exito');
	if (paramExito != null){
		if(paramExito == "1"){
			mostrarNotificacionExito("Pregunta registrada correctamente.");
		}else if(paramExito == "2"){
			mostrarNotificacionExito("Pregunta editada correctamente.");
		}else if(paramExito == "3"){
			mostrarNotificacionExito("Pregunta eliminada correctamente.");
		}
	}
	var paramError = getURLParameter('error');
	if (paramError != null){
		if(paramError == "1"){
			mostrarNotificacionError("Error al registrar pregunta.");
		}else if(paramError == "2"){
			mostrarNotificacionError("Error al editar pregunta.");
		}else if(paramError == "3"){
			mostrarNotificacionError("Error al eliminar pregunta.");
		}else if(paramError == "4"){
			mostrarNotificacionError("Error al cargar pregunta.");
		}else if(paramError == "5"){
			mostrarNotificacionError("Error al eliminar, hay otras preguntas que tienen como precedente a esta.");
		}else if(paramError == "6"){
			mostrarNotificacionError("Error al cargar temas.");
		}else if(paramError == "7"){
			mostrarNotificacionError("Error al cargar preguntas del tema seleccionado.");
		}
	}	
}

function mostrarNotificacionUsuario(){
	var paramError = getURLParameter('error');
	if (paramError != null){
		if(paramError == "4"){
			mostrarNotificacionError("Error al cargar usuario.");
		}
	}
}

function mostrarNotificacionConsulta() {
	var paramExito = getURLParameter('exito');
	if (paramExito != null){
		if(paramExito == "1"){
			mostrarNotificacionExito("Consulta respondida correctamente.");
		}
	}
	var paramError = getURLParameter('error');
	if (paramError != null){
		if(paramError == "1"){
			mostrarNotificacionError("Error al obtener consulta.");
		}else if(paramError == "2"){
			mostrarNotificacionError("Error al intentar leer la consulta.");
		}else if(paramError == "3"){
			mostrarNotificacionError("Error al cargar consulta para contestar.");
		}else if(paramError == "4"){
			mostrarNotificacionError("Error al intentar responder la consulta.");
		}
	}
}

// ---------------------------------------------------------------------
// -----------------------   SESION Y LOGIN   --------------------------

function guardarDatosSesion(val){
	window.localStorage.setItem("user_token", val.token_key);
	window.localStorage.setItem("user_id", val.id);
	window.localStorage.setItem("user_nombre", val.nombre);
	window.localStorage.setItem("user_email", val.email);
}

function setearNombreAdministrador(){
	var nombreAdmin = window.localStorage.getItem("user_nombre");
	$(".nombreAdmin").html(nombreAdmin);
}

function verificarAdminLogueadoIndex(){
	var token = window.localStorage.getItem("user_token");
	if (token !== 'undefined' && token !== null){
		//esta logueado
	}else{
		window.location = "pages/login.html";
	}
}

function verificarAdminLogueado(){
	var token = window.localStorage.getItem("user_token");
	if (token !== 'undefined' && token !== null){
		//esta logueado
	}else{
		window.location = "login.html";
	}
}

function limpiarDatosSesion(){
	window.localStorage.removeItem("user_token");
	window.localStorage.removeItem("user_id");
	window.localStorage.removeItem("user_nombre");
	window.localStorage.removeItem("user_email");
}

function login() {
	$.ajax({
		url : baseUrl + "/session/login/profesor",
		type: "GET",
		dataType: "json",
		data: $("#frmLogin").serialize(),
		success: function(val){
			guardarDatosSesion(val);
			window.location = "../index.html";
		},
		error: function(){
			mostrarNotificacionAlerta("Login incorrecto. Verifique sus datos.");
		},
	});
}


// ---------------------------------------------------------------------
// -----------------------   PANEL PRINCIPAL   -------------------------

function obtenerDatosDashboard(){
	var token = window.localStorage.getItem("user_token");
	var cant = 0;
	$.ajax({
		url : baseUrl + "/dashboard/estadisticas",
		type: "GET",
		dataType: "json",
		//headers: { 'Authorization' : token},
		success: function(val){
			setearDatosDashboard(val);
		},
		error: function(){
			mostrarNotificacionError("A ocurrido un error al intentar cargar los datos del panel principal.");
		},
	});
	return cant;	
}

function setearDatosDashboard(val){
	$("#cantidadRespuestas").html(val.cantidadRespuestas);
	var porcentaje = 0;
	if(!isNaN(val.procentajeRespuestasCorrectas)){
		porcentaje = val.procentajeRespuestasCorrectas.toFixed(2);
	}
	$("#respuestasCorrectas").html(porcentaje + " <sup style='font-size: 20px'>%</sup>");
	$("#usuariosRegistrados").html(val.cantidadAlumnos);
	$("#cantidadConsultas").html(val.cantidadDudas);
	aplicarDataTablePreguntasDificiles(val.preguntasDificiles);
	obtenerRankingUsuarios(1);
}

function aplicarDataTablePreguntasDificiles(data){
	//var jsonData = JSON.stringify(data); //creo que ahora no esta viniendo como Json
	$('#preguntasDificilesTable').DataTable({
		"paging": true,
		"lengthChange": false,
		"searching": false,
		"ordering": false,
		"info": false,
		"autoWidth": true,
		"aaData": data,
		"aoColumns": [
			{ "sTitle": "Id", "mData" : "id"},
			{ "sTitle": "Tema", "mData" : "tema.tema" },
			{ "sTitle": "Título", "mData" : "titulo" },
			{ "sTitle": "Pregunta", "mData" : "descripcion.texto" },
			{ "sTitle": "Puntaje", "mData" : "puntos" },
			{ "sTitle": "% Resp. correctas", "mRender" :
				function (data, type, row) {
					var porcentaje = 0;
					if(!isNaN(row.procentajeRespuestasCorrectas)){
						porcentaje = row.procentajeRespuestasCorrectas;
					}
					return '<span class="badge bg-red">' + porcentaje.toFixed(2) + ' %</span>'
				}
			},
			{ "sTitle": "Editar", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a href="pages/editarpregunta.html?id=' + row.id + '"><i class="fa fa-pencil-square-o"></i></a>';
				}					
			}
		]
	});
}

// ---------------------------------------------------------------------
// -----------------------   LISTAR   ----------------------------------


//Listado de Preguntas con DataTables
function obtenerPreguntasListado() {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/preguntas",
		type: "GET",
		dataType: "json",
		//headers: { 'Authorization' : token},
		success: function(val){
			aplicarDataTablePreguntas(val);
		},
		error: function(){
			mostrarNotificacionError("A ocurrido un error al intentar cargar las preguntas.");
		},
	});
}

function aplicarDataTablePreguntas(data){
	//var jsonData = JSON.stringify(data); //creo que ahora no esta viniendo como Json
	$('#preguntasTable').DataTable({
		"paging": true,
		"lengthChange": true,
		"searching": true,
		"ordering": true,
		"info": true,
		"autoWidth": true,
		"aaData": data,
		"aoColumns": [
			{ "sTitle": "Id", "mData" : "id"},
			{ "sTitle": "Título", "mData" : "titulo" },
			{ "sTitle": "Tema", "mData" : "tema.tema" },
			{ "sTitle": "Puntaje", "mData" : "puntos" },
			//{ "sTitle": "Fecha de creación", "mData" : "fechacreacion" },
			{ "sTitle": "Cantidad de respuestas", "mData" : "cantRespuestas" },
			{ "sTitle": "Respuestas correctas", "mRender" :
				function (data, type, row) {
					var porcentajeCorrectas = row.procentajeRespuestasCorrectas;
					if (isNaN(porcentajeCorrectas)){
						porcentajeCorrectas = 0;
					}
					return porcentajeCorrectas.toFixed(2) + '%';
				}
			},
			{ "sTitle": "Ver detalle", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a href="detallepregunta.html?id=' + row.id + '"><i class="fa fa-search"></i></a>';
				}
			},
			{ "sTitle": "Editar", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a href="editarpregunta.html?id=' + row.id + '"><i class="fa fa-pencil-square-o"></i></a>';
				}					
			},
			{ "sTitle": "Eliminar", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a class="btnEliminarPregunta" href="#" data-idpreg="' + row.id + '" data-pregunta="' + row.titulo + '"><i class="fa fa-trash-o"></i></a>';
				}					
			}
		]
	});
}


//Listado de Temas con DataTables
function obtenerTemasListado() {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/temas",
		type: "GET",
		dataType: "json",
		//headers: { 'Authorization' : token},
		success: function(val){
			aplicarDataTableTemas(val);
		},
		error: function(){
			mostrarNotificacionError("A ocurrido un error al intentar cargar los temas.");
		},
	});
}

function aplicarDataTableTemas(data){
	//var jsonData = JSON.stringify(data); //creo que ahora no esta viniendo como Json
	$('#temasTable').DataTable({
		"paging": true,
		"lengthChange": true,
		"searching": true,
		"ordering": true,
		"info": true,
		"autoWidth": true,
		"aaData": data,
		"aoColumns": [
			{ "sTitle": "Id", "mData" : "id"},
			{ "sTitle": "Tema", "mData" : "tema" },
			{ "sTitle": "Descripción", "mData" : "descripcion" },
			//{ "sTitle": "Fecha de creación", "mData" : "fechacreacion" },
			//{ "sTitle": "Cantidad de preguntas", "mData" : "puntaje" },
			{ "sTitle": "Ver detalle", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a href="detalletema.html?id=' + row.id + '"><i class="fa fa-search"></i></a>';
				}					
			},
			{ "sTitle": "Editar", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a href="editartema.html?id=' + row.id + '"><i class="fa fa-pencil-square-o"></i></a>';
				}					
			},
			{ "sTitle": "Eliminar", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a class="btnEliminarTema" href="#" data-idtema="' + row.id + '" data-titulotema="' + row.tema + '" ><i class="fa fa-trash-o"></i></a>';
				}
			}
		]
	});
}

function obtenerRankingUsuarios(opcion) {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/alumnos/ranking",
		type: "GET",
		dataType: "json",
		headers: { 'Authorization' : token},
		success: function(data){
			if (opcion == 1) {
				var topTen = [];
			
				$.each(data, function(i, user) {
					if (i < 10) {
						topTen.push(user);
					}
				});
				
				$('#usuariosRankingTable').DataTable({
					"paging": false,
					"lengthChange": false,
					"searching": false,
					"ordering": false,
					"info": false,
					"autoWidth": true,
					"aaData": topTen,
					"aoColumns": [
						{ "sTitle": "Posición", "bSortable" : false, "mData" : "posicion" },
						{ "sTitle": "Nombre", "bSortable" : false, "mData" : "nombre" },
						{ "sTitle": "Email", "bSortable" : false, "mData" : "email" },
						{ "sTitle": "Puntaje", "bSortable" : false, "mData" : "puntaje" }
					]
				});
			} else {
				$('#usuariosRankingTable').DataTable({
					"paging": true,
					"lengthChange": true,
					"searching": true,
					"ordering": true,
					"info": false,
					"autoWidth": true,
					"aaData": data,
					"aoColumns": [
						{ "sTitle": "Posición", "bSortable" : true, "mData" : "posicion" },
						{ "sTitle": "Nombre", "bSortable" : false, "mData" : "nombre" },
						{ "sTitle": "Email", "bSortable" : false, "mData" : "email" },
						{ "sTitle": "Puntaje", "bSortable" : false, "mData" : "puntaje" },
						{ "sTitle": "Ver detalle", "bSortable" : false, "mRender" : 	
							function (data, type, row) {
								return '<a href="detalleusuario.html?id=' + row.id + '"><i class="fa fa-search"></i></a>';
							}
						}
					]
				});
			}
		},
		error: function(){
			mostrarNotificacionError("A ocurrido un error al intentar cargar el ranking de estudiantes.");
		},
	});
}

//Listado de Usuarios con DataTables
function obtenerUsuariosListado() {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/alumnos",
		type: "GET",
		dataType: "json",
		//headers: { 'Authorization' : token},
		success: function(val){
			aplicarDataTableUsuarios(val);
		},
		error: function(){
			mostrarNotificacionError("A ocurrido un error al intentar cargar los estudiantes.");
		},
	});
}

function aplicarDataTableUsuarios(data){
	//var jsonData = JSON.stringify(data); //creo que ahora no esta viniendo como Json
	$('#usuariosTable').DataTable({
		"paging": true,
		"lengthChange": true,
		"searching": true,
		"ordering": true,
		"info": true,
		"autoWidth": true,
		"aaData": data,
		"aoColumns": [
			{ "sTitle": "Id", "mData" : "id" },
			{ "sTitle": "Nombre", "mData" : "nombre" },
			{ "sTitle": "Email", "mData" : "email" },
			//{ "sTitle": "Fecha de registro", "mData" : "fechacreacion" },
			{ "sTitle": "Puntaje", "mData" : "puntaje" },
			//{ "sTitle": "Ranking", "mRender" : },
			{ "sTitle": "Cantidad de respuestas", "mRender" :
				function (data, type, row) {
					var cantidadTotal = row.cantidadPreguntasIncorrectas + row.cantidadPreguntasCorrectas;
					return cantidadTotal;
				}
			},
			{ "sTitle": "Respuestas correctas", "mRender" :
				function (data, type, row) {
					var porcentajeCorrectas = (row.cantidadPreguntasCorrectas / (row.cantidadPreguntasIncorrectas + row.cantidadPreguntasCorrectas)) * 100;
					if (isNaN(porcentajeCorrectas)){
						porcentajeCorrectas = 0;
					}
					return porcentajeCorrectas.toFixed(2) + '%';
				}
			},
			{ "sTitle": "Ver detalle", "bSortable" : false, "mRender" : 	
				function (data, type, row) {
					return '<a href="detalleusuario.html?id=' + row.id + '"><i class="fa fa-search"></i></a>';
				}
			}
		]
	});
}

//Listado de preguntaAlumno con DataTables
function obtenerPreguntaAlumnoListado(id_usuario) {
	var token = window.localStorage.getItem("user_token");

	$.ajax({
		url : baseUrl + "/alumnos/" + id_usuario + "/ranking",
		type: "GET",
		dataType: "json",
		headers: { 'Authorization' : token},
		success: function(val){

			$("#username_title").text(val.nombre + " - " + val.puntaje +"pts");
			$("#id_user").text(val.id);
			$("#email").text(val.email);
			$("#ranking").text(val.posicion + "/" + val.cantAlumnos);
			$("#correctas").text(val.respuestasCorrectas);
			$("#incorrectas").text(val.respuestasIncorrectas);
			var porcentajeCorrectas = (val.respuestasCorrectas / (val.respuestasCorrectas + val.respuestasIncorrectas)) * 100
			if (isNaN(porcentajeCorrectas)){
				porcentajeCorrectas = 0;
			}
			$("#acierto").text(porcentajeCorrectas.toFixed(2) + '%');
		},
		error: function(){
			
		},
	});

	$.ajax({
		url : baseUrl + "/alumnos/" + id_usuario + "/progreso",
		type: "GET",
		dataType: "json",
		headers: { 'Authorization' : token},
		success: function(val){
			aplicarDataTablePreguntaAlumno(val);
		},
		error: function(){
			
		},
	});
}

function aplicarDataTablePreguntaAlumno(data){
	//var jsonData = JSON.stringify(data); //creo que ahora no esta viniendo como Json
	console.log(data);
	$('#historialUsuarioTable').DataTable({
		"paging": true,
		"lengthChange": false,
		"searching": true,
		"ordering": false,
		"info": true,
		"autoWidth": false,
		"aaData": data,
		"aoColumns": [
			{ "sTitle": "Id", "mData" : "pregunta.id"},
			{ "sTitle": "Título", "mData" : "pregunta.titulo" },
			{ "sTitle": "Puntaje", "mData" : "pregunta.puntos"},
			{ "sTitle": "Bloqueada", "mRender" :
				function (data, type, row) {
					if(row.blocked){
						return 'Sí';
					}else{
						return 'No';
					}
				}
			},	
			{ "sTitle": "Respuestas incorrectas", "mRender" :
				function (data, type, row) {
					return row.respuestaIncorrecta;
				}
			},
			{ "sTitle": "Respuesta correcta", "mRender" :
				function (data, type, row) {
					if(row.respuestaCorrecta){
						return "Sí";
					} else {
						return "No";
					}
				}
			}
		]
	});
}

//Listado de Consultas con DataTables
function obtenerConsultasListado() {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/dudas",
		type: "GET",
		dataType: "json",
		headers: { 'Authorization' : token},
		success: function(val){
			aplicarDataTableConsultas(val);
		},
		error: function(){
			mostrarNotificacionError("A ocurrido un error al intentar cargar las consultas.");
		},
	});
}

function aplicarDataTableConsultas(data){
	//var jsonData = JSON.stringify(data); //creo que ahora no esta viniendo como Json
	$('#consultasTable').DataTable({
		"paging": true,
		"lengthChange": true,
		"searching": true,
		"ordering": true,
		"info": true,
		"autoWidth": false,
		"aaData": data,
		"aoColumns": [
			{ "sTitle": "Usuario", "mData" : "alumno.email"},
			{ "sTitle": "Nombre", "mData" : "alumno.nombre"},
			{ "sTitle": "Motivo", "mData" : "motivo" },
			{ "sTitle": "Fecha", "mData" : "fecha" },
			{ "sTitle": "Leída", "mRender" : 	
				function (data, type, row) {
					if(row.leida){
						return 'Sí';
					}else{
						return 'No';
					}
				}
			},
			{ "sTitle": "Contestada", "mRender" : 	
				function (data, type, row) {
					if(row.respuesta != null){
						return 'Sí';
					}else{
						return 'No';
					}
				}
			},
			{ "sTitle": "Abrir", "bSortable" : false, "mRender" :
				function (data, type, row) {
					return '<a class="btnLeerConsulta" href="#" data-idconsulta="' + row.id + '"><i class="fa fa-folder-open-o"></i></a>';
				}
			}
		]
	});
}

//Responder Consulta
function responderConsulta(id) {
    var token = window.localStorage.getItem("user_token");

    if (!$("#compose-textarea").val().trim()) {
        mostrarNotificacionError("La respuesta a la consulta no puede ser vacia.");
    } else if ($("#compose-textarea").val().length > 255) {
        mostrarNotificacionError("La respuesta es demasiado larga.");
    } else {
        $.ajax({
            //url: baseUrl + "/dudas/" + id + "/respuesta?r={" + $('#compose-textarea').data('wysihtml5').editor.getValue() + "}",
            url: baseUrl + "/dudas/" + id + "/respuesta?r=" + $("#compose-textarea").val(),
            type: "PUT",
            headers: {"Authorization": token},
            success: function () {
                window.location = "inboxconsultas.html?exito=1";
            },
            error: function () {
                window.location = "inboxconsultas.html?error=4"
            }
        });
    }
}

function reiniciarForma() {
	$("#asunto").val("");
	$("#compose-textarea").val("");
	//$('#compose-textarea').data('wysihtml5').editor.clear();
}

function cargarCamposResponder(duda) {
	$("#motivo").val(duda.motivo);
	$("#destinatario").val(duda.alumno.nombre + " - " + duda.alumno.email);
	$("#asunto").val("Re: " + duda.duda);
	if (duda.respuesta != null) {
		$("#compose-textarea").val(duda.respuesta);
	} else {
		$("#div-enviar").toggle();
	}
}

function obtenerConsultaResponder(id) {
	var token = window.localStorage.getItem("user_token");
	
	$.ajax({
		url : baseUrl + "/dudas/" + id,
		type: "GET",
		dataType: "json",
		headers: { 'Authorization' : token},
		success: function(val){
			cargarCamposResponder(val);
		},
		error: function(){
			window.location = "inboxconsultas.html?error=3";
		},
	});
}

function leerConsulta(id) {
	var token = window.localStorage.getItem("user_token");
	
	$.ajax({
		url: baseUrl + "/dudas/" + id + "/leida?l=true",
		type: "PUT",
		headers : { "Authorization": token },
		success: function() {
			window.location = "leerconsulta.html?id=" + id;
		},
		error: function() {
			window.location = "inboxconsultas.html?error=2";
		}
	});
}

function obtenerConsulta(id) {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/dudas/" + id,
		type: "GET",
		dataType: "json",
		headers: { 'Authorization' : token},
		success: function(val){
			completarCamposConsulta(val);
		},
		error: function(){
			window.location = "inboxconsultas.html?error=1";
		},
	});
}

function completarCamposConsulta(data){
	$("#tituloConsulta").html(data.motivo);
	$("#usuarioConsulta").html("De:  " + data.alumno.nombre + " (" + data.alumno.email + ")");
	$("#fechaConsulta").html("Fecha:  " + data.fecha);
	$("#mensajeConsulta").html(data.duda);

	if(data.respuesta != null && data.respuesta != ""){
		$("#btnResponder").hide();
		$("#btnVolver").hide();
		$("#divRespuesta").show();		
		$("#mensajeRespuesta").html(data.respuesta);	
		$("#btnVolverRespuesta").show();
	}
}



// ---------------------------------------------------------------------
// -----------------------   REGISTRAR   -------------------------------

// Registrar tema
function registrarTema() {
	var token = window.localStorage.getItem("user_token");
	var formData = $("#formRegistrarTema").serializeJSON();
	$.ajax({
		url : baseUrl + "/temas",
		type: "POST",
		contentType: "application/json",
		headers : { 
			"Authorization":token
		},
		data: formData,
		success: function(){
			window.location = "listartemas.html?exito=1";		
		},
		error: function(e){
			if (e.status == 403) {
				mostrarNotificacionError("Ya existe un tema con ese título.");
			} else {
				window.location = "listartemas.html?error=1";
			}
		},
	});
}

function verificarRegistroTema(accion) {
	var camposCorrectos = true;
        var idError;
        var pattern = /^\s*$/;
        // campos no vacios
	if (pattern.test($("#tema").val())) {
		camposCorrectos = false;
                idError = 1;
		$("#tema").prev().css("color", "red");
	} else {
		$("#tema").prev().css("color", "black");
	};
	if (pattern.test($("#descripcion").val())) {
		camposCorrectos = false;
                idError = 1;
		$("#descripcion").prev().css("color", "red");
	} else {
		$("#descripcion").prev().css("color", "black");
	};
        
        // largo de campos menor o igual a 255, solo mostramos error si ya se comprobo el requerido
        if (camposCorrectos)
        {
            if ($("#tema").val().length > 255) {
                    camposCorrectos = false;
                    idError = 2;
                    $("#tema").prev().css("color", "red");
            } else {
                    $("#tema").prev().css("color", "black");
            };
            if ($("#descripcion").val().length > 255) {
                    camposCorrectos = false;
                    idError = 2;
                    $("#descripcion").prev().css("color", "red");
            } else {
                    $("#descripcion").prev().css("color", "black");
            };
        }
        
	if (camposCorrectos) {
		if (accion == "registrar") {
			registrarTema();
		} else {
			editarTema();
		}
	} else {
                if (idError == 1) {
        		mostrarNotificacionError("Falta completar campos requeridos.");
                } else if (idError == 2)
                {
        		mostrarNotificacionError("Hay campos con largo mayor al permitido.");
                }
	}
}

// Registrar pregunta
function cargarTemas(temas, sel) {
	var html = "";
	$.each(temas, function(i, tema) {
		html += '<option value=' + tema.id + '>' + tema.tema + '</option>';
	});
	$("#temas").append(html);
	if (sel !== undefined) {
		$("#temas").val(sel);
	}
}

function obtenerTemas(sel) {
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/temas",
		type : "GET",
		dataType : "json",
	
		success: function(val) {
			cargarTemas(val, sel);
		},
		error: function() {
			window.location = "listarpreguntas.html?error=6";
		}	
	});
}

function cargarPreguntas(preguntas, padre, sel) {
	var html = "";
	$.each(preguntas, function(i, pregunta) {
		if (pregunta.id != padre) {
			html += '<option value=' + pregunta.id + '>' + pregunta.titulo + '</option>';
		}
	});
	$("#desbloquea").append(html).trigger('change');
	if (sel !== undefined) {
		$("#desbloquea").select2().val(sel).trigger('change')
	}
}

function obtenerPreguntasTema(tema, pregunta, sel) {
	var token = window.localStorage.getItem("user_token");
    var url = baseUrl + "/temas/" + tema + "/preguntas/";
	$("#desbloquea").empty();
	
	$.ajax({
		url : url,
		type : "GET",
		dataType : "json",
		headers : { "Authorization":token },
        success: function(val) {
			cargarPreguntas(val, pregunta, sel);
        },
		error: function() {
			window.location = "listarpreguntas.html?error=7";
		}
	});
}

function registrarPregunta() {
    var jsonPrevias = "";
    var cont = 0;
	var selected = $("#desbloquea").val();
	$.each(selected, function(i, item) {
		if(cont == 0) {
			jsonPrevias += "[" + item;
		} else {
			jsonPrevias += "," + item;
		}
		cont++;
	});
    if(cont > 0) {
		jsonPrevias += "]";
    } else { 
		jsonPrevias = "null"; 
	}
	var json = "{\"respuesta\": \"" + $("#respuesta").val() + "\",\"puntos\": " + $("#puntos").val() + ",\"inicial\": " + $("#inicial").is(":checked")
	+ ",\"titulo\": \"" + $("#titulo").val() + "\",\"profesor\": null,\"descripcion\":{\"texto\": \"" + $("#descripcion_texto").val() + "\",\"imagen\": \"" +
	$("#descripcion_imagen").val() + "\"},\"tip\": {\"texto\": \"" + $("#tip_texto").val() + "\",\"imagen\": \"" + $("#tip_imagen").val() + 
	"\",\"video\": \"" + $("#tip_video").val() + "\"},\"tema\": {\"id\":" + $("#temas").val() + "},\"type\": \"completar\",\"preguntasPrevias\":" 
	+ jsonPrevias +"}";
	var token = window.localStorage.getItem("user_token");
	
	//console.log(json);

	$.ajax({
		url : baseUrl + "/preguntas",
		type: "POST",
		contentType: "application/json",
		headers : { 
			"Authorization":token
		},
		data: json,
		success: function(data, textStatus, xhr){
			//agregarDesbloquea(xhr.getResponseHeader("Location").split("/").pop());
            window.location = "listarpreguntas.html?exito=1";
		},
		error: function(e){
			if (e.status == 403) {
				mostrarNotificacionError("Ya existe una pregunta con ese título en ese tema.");
			} else {
				window.location = "listarpreguntas.html?error=1";
			}
		},
	});
}

function verificarRegistroPregunta(accion, id) {
    
    var errorId = 0;
    // pasamos todos los campos a black
    $("#temas, #titulo, #descripcion_texto, #respuesta, #puntos, #descripcion_imagen, #tip_texto, #tip_imagen, #tip_video")
            .each(function (index) {
                $(this).prev().css("color", "black");
            });

    // mostramos en rojo los campos con errores
    // chequeo de campos vacíos
    if ($("#temas").val() == null) {
        errorId = 1;
        $("#temas").prev().css("color", "red");
    }
    $("#titulo, #descripcion_texto, #respuesta, #puntos, #tip_texto")
            .each(function (index) {
                if ($(this).val().trim().length == 0) {
                    errorId = 1;
                    $(this).prev().css("color", "red");
                }
            });

    // largo de campos menor o igual a 255, solo mostramos error si ya se comprobo el requerido
    if (errorId == 0) {
        $("#titulo, #descripcion_texto, #respuesta, #puntos, #descripcion_imagen, #tip_texto, #tip_imagen, #tip_video")
                .each(function (index) {
                    if ($(this).val().length > 255) {
                        errorId = 4;
                        $(this).prev().css("color", "red");
                    }
                });
    }

    // puntos debe ser un numero y positivo
    if (errorId == 0) {
        if (!($("#puntos").val() > 0)) {
            errorId = 5;
            $("#puntos").prev().css("color", "red");
        }
    }

    if (errorId == 0 && !$("#inicial").is(":checked") && $("#desbloquea").val() == null) {
        errorId = 2;
    }

    var videoPatt = new RegExp("^https://www\\.youtube\\.com/watch\\?v=(.)*$");
    if (errorId == 0 && $("#tip_video").val() != "" && !videoPatt.test($("#tip_video").val())) {
        errorId = 3;
        $("#tip_video").prev().css("color", "red");
    }

    if (errorId == 0) {
        if (accion == "registrar") {
            registrarPregunta();
        } else {
            editarPregunta(id);
        }
    } else if (errorId == 1) {
        mostrarNotificacionError("Falta completar campos requeridos.");
    } else if (errorId == 2) {
        mostrarNotificacionError("La pregunta debe ser inicial o desbloqueada por otra pregunta.");
    } else if (errorId == 3) {
        mostrarNotificacionError("El url del video debe comenzar con 'https://www.youtube.com/watch?v='.");
    } else if (errorId == 4) {
        mostrarNotificacionError("El largo de los campos es mayor al permitido.");
    } else if (errorId == 5) {
        mostrarNotificacionError("Puntos debe ser un número positivo.");
    }
}

// ---------------------------------------------------------------------
// -----------------------   EDITAR   ----------------------------------

// Editar Tema
function cargarEditarTema(id){
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/temas/" + id,
		type : "GET",
		dataType : "json",
		headers : { "Authorization": token },
        success: function(val) {
			cargarCamposEditarTema(val);
        },
		error: function() {
			window.location = "listartemas.html?error=4";
		}
	});
}

function cargarCamposEditarTema(data){
	$("#id").val(data.id);
	$("#tema").val(data.tema);
	$("#descripcion").val(data.descripcion);
}

function editarTema() {
	var token = window.localStorage.getItem("user_token");
	var formData = $("#formEditarTema").serializeJSON();
	$.ajax({
		url : baseUrl + "/temas",
		type: "PUT",
		contentType: "application/json",
		headers : { "Authorization": token },
		data: formData,
		success: function() {
			window.location = "listartemas.html?exito=2";
		},
		error: function(e){
			if (e.status == 403) {
				mostrarNotificacionError("Ya existe un tema con ese título.");
			} else {
				window.location = "listartemas.html?error=2"; //si se quiere redirigir usar este
				//mostrarNotificacionError("Error al editar tema.");
			}
		},
	});
}

//Editar Pregunta
function cargarCamposEditarPregunta(data) {
	obtenerTemas(data.tema.id);
	$("#titulo").val(data.titulo);
	$("#descripcion_texto").val(data.descripcion.texto);
	if(data.inicial == true) {
		$("#inicial").prop('checked', true);
		$(".select2").val('').change();
		$('#div-bloquean').hide();
	} else {
		$("#inicial").prop('checked', false);
	}
	$("#respuesta").val(data.respuesta);
	$("#puntos").val(data.puntos);
	
	obtenerPreguntasTema(data.tema.id, data.id, data.preguntasPrevias);
	
	$("#descripcion_imagen").val(data.descripcion.imagen);
	$("#tip_texto").val(data.tip.texto);
	$("#tip_imagen").val(data.tip.imagen);
	$("#tip_video").val(data.tip.video);
}

function cargarEditarPregunta(id) {
	var token = window.localStorage.getItem("user_token");
	
	$.ajax({
		url : baseUrl + "/preguntas/" + id,
		type : "GET",
		dataType : "json",
		headers : { "Authorization": token },
        success: function(val) {
			cargarCamposEditarPregunta(val);
        },
		error: function() {
			window.location = "listarpreguntas.html?error=4";
		}
	});
}
/*
function agregarDesbloquea(pregunta)
{
	var json ="";
	var token = window.localStorage.getItem("user_token");
	var cont = 0;
	var selected = $("#desbloquea").val();
	$.each(selected, function(i, item) {
		if(cont == 0) {
			json += "[" + item;
		} else {
			json += "," + item;
		}
		cont++;
	});
	if(cont > 0) {
		json += "]";
	
		$.ajax({
			url : baseUrl + "/preguntas/" + pregunta + "/addDesbloqueo",
			type: "PUT",
			dataType: "json",
			contentType: "application/json",
			headers : { 
				"Authorization":token
			},
			data: json,
			success: function(){
				window.location = "listarpreguntas.html?exito=2";
			},
			error: function(){
				mostrarNotificacionError("Error al editar pregunta.");
				//window.location = "listarpreguntas.html?error=2"; //si se quiere redirigir usar este
			}
		});
	} else {
		window.location = "listarpreguntas.html?exito=2";
	}
}*/

function editarPregunta(id) {
	var jsonPrevias = "";
    var cont = 0;
	var selected = $("#desbloquea").val();
	$.each(selected, function(i, item) {
		if(cont == 0) {
			jsonPrevias += "[" + item;
		} else {
			jsonPrevias += "," + item;
		}
		cont++;
	});
    if(cont > 0) {
		jsonPrevias += "]";
    } else { 
		jsonPrevias = "null"; 
	}
	
	var token = window.localStorage.getItem("user_token");
	var json = "{\"id\": " + id +",\"respuesta\": \"" + $("#respuesta").val() + "\",\"puntos\": " + $("#puntos").val() + ",\"inicial\": " + 
	$("#inicial").is(":checked") + ",\"titulo\": \"" + $("#titulo").val() + "\",\"profesor\": null,\"descripcion\":{\"texto\": \"" 
	+ $("#descripcion_texto").val() + "\",\"imagen\": \"" + $("#descripcion_imagen").val() + "\"},\"tip\": {\"texto\": \"" + $("#tip_texto").val() + 
	"\",\"imagen\": \"" + $("#tip_imagen").val() + "\",\"video\": \"" + $("#tip_video").val() + "\"},\"tema\": {\"id\":" + $("#temas").val() +
	"},\"type\": \"completar\",\"preguntasPrevias\":" + jsonPrevias +"}";
	
	$.ajax({
		url : baseUrl + "/preguntas",
		type: "PUT",
		contentType: "application/json",
		headers : { "Authorization": token },
		data: json,
		success: function() {
			//agregarDesbloquea(id);
			window.location = "listarpreguntas.html?exito=2";
		},
		error: function(e) {
			if (e.status == 403) {
				mostrarNotificacionError("Ya existe una pregunta con ese título en ese tema.");
			} else {
				window.location = "listarpreguntas.html?error=2"; //si se quiere redirigir usar este
				//mostrarNotificacionError("Error al editar pregunta.");
			}
		}
	});
}

// ---------------------------------------------------------------------
// -----------------------   VER DETALLE   -----------------------------

// Detalle Tema
function cargarDetalleTema(id){
	var token = window.localStorage.getItem("user_token");
	$.ajax({
		url : baseUrl + "/temas/" + id,
		type : "GET",
		dataType : "json",
		headers : { "Authorization": token },
        success: function(val) {
			cargarCamposEditarTema(val);
        },
		error: function() {
			window.location = "listartemas.html?error=4";
		}
	});
}

//Detalle Pregunta
function obtenerTitulosPreguntas(preguntas) {
	var token = window.localStorage.getItem("user_token");
	
	$.each(preguntas, function(i, pregunta) {
		$.ajax({
			url : baseUrl + "/preguntas/" + pregunta,
			type : "GET",
			dataType : "json",
			headers : { "Authorization":token },
			success: function(val) {
				$("#desbloquea").append('<option>' + val.titulo + '</option>').trigger('change');
			}
		});
	});
}

function cargarCamposDetallePregunta(data) {
	$("#temas").val(data.tema.tema);
	$("#titulo").val(data.titulo);
	$("#descripcion_texto").val(data.descripcion.texto);
	if(data.inicial == true) {
		$("#inicial").prop('checked', true);
		$('#div-bloquean').hide();
	} else {
		$("#inicial").prop('checked', false);
	}
	$("#respuesta").val(data.respuesta);
	$("#puntos").val(data.puntos);
	
	obtenerTitulosPreguntas(data.preguntasPrevias);
	
	$("#descripcion_imagen").val(data.descripcion.imagen);
	$("#tip_texto").val(data.tip.texto);
	$("#tip_imagen").val(data.tip.imagen);
	$("#tip_video").val(data.tip.video);
}

function cargarDetallesPregunta(id) {
	var token = window.localStorage.getItem("user_token");
	
	$.ajax({
		url : baseUrl + "/preguntas/" + id,
		type : "GET",
		dataType : "json",
		headers : { "Authorization": token },
        success: function(val) {
			cargarCamposDetallePregunta(val);
        },
		error: function() {
			window.location = "listarpreguntas.html?error=4";
		}
	});
}

//detalles usuario


// ---------------------------------------------------------------------
// -----------------------   ELIMINAR   --------------------------------

//Eliminar Tema
function eliminarTema(id) {
	var token = window.localStorage.getItem("user_token");
	var formData = $("#formEliminarTema").serializeJSON();
	$.ajax({
		url : baseUrl + "/temas/" + id,
		type: "DELETE",
		//contentType: "application/json",
		headers : { "Authorization": token },
		//data: formData,
		success: function(){
			window.location = "listartemas.html?exito=3";
		},
		error: function(jqXHR,textStatus, errorThrown){
			if (jqXHR.status == 409) {
				window.location = "listartemas.html?error=5";
			} else {
				window.location = "listartemas.html?error=3";
			}
			//mostrarNotificacionError("Error al eliminar tema. Verifique que no tenga preguntas."); //si no se quiere redirigir usar este
		},
	});
}

/*
//Eliminar Pregunta
function quitarDesbloqueo(id, preguntas) {
	var token = window.localStorage.getItem("user_token");
	
	$.ajax({
		url : baseUrl + "/preguntas/" + id + "/removeDesbloqueo",
		type: "PUT",
		dataType: "json",
		contentType: "application/json",
		headers : { 
			"Authorization":token
		},
		data: preguntas,
		success: function(){
			window.location = "listarpreguntas.html?exito=3";
		},
		error: function(){
			window.location = "listarpreguntas.html?error=3";
		}
	});
}*/
/*
function quitarPreguntasDesbloquea(id) {
	var token = window.localStorage.getItem("user_token");
	
	$.ajax({
		url : baseUrl + "/preguntas/" + id,
		type : "GET",
		dataType : "json",
		headers : { "Authorization": token },
        success: function(val) {
			quitarDesbloqueo(id, val.preguntasQueDesbloquea);
        },
		error: function() {
			window.location = "listarpreguntas.html?error=3";
		}
	});
}*/

function eliminarPregunta(id) {
	var token = window.localStorage.getItem("user_token");
	
	//quitarPreguntasDesbloquea(id);
	
	$.ajax({
		url: baseUrl + "/preguntas/" + id,
		type: "DELETE",
		headers: { "Authorization": token },
		success: function() {
			window.location = "listarpreguntas.html?exito=3";
		},
		error: function(jqXHR, textStatus, errorThrown){
			if (jqXHR.status == 409) {
				window.location = "listarpreguntas.html?error=5";
			} else {
				window.location = "listarpreguntas.html?error=3";
			}
			//mostrarNotificacionError("Error al eliminar pregunta. No se puede borrar preguntas que han sido contestadas por alumnos.");
		},
	});
}

// ---------------------------------------------------------------------
// -----------------------   AUXILIARES   ------------------------------

function getURLParameter(sParam){
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
}