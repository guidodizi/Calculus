/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller;

/**
 *
 * @author mariano
 */
import calculus.backend.CalculusBackendConfiguration;
import calculus.backend.JpaConfig;
import calculus.backend.controller.v2.InitController;
import calculus.backend.controller.v2.game.PreguntaController;
import calculus.backend.controller.v2.admin.TemaController;
import calculus.backend.model.Alumno;
import calculus.backend.model.Duda;
import calculus.backend.model.Informacion;
import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.PreguntaCompletar;
import calculus.backend.model.Profesor;
import calculus.backend.model.RespuestaPregunta;
import calculus.backend.model.Tema;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JpaConfig.class, CalculusBackendConfiguration.class})
public class GamePreguntaControllerTest {
    @Autowired
    private PreguntaController preguntaController;
    @Autowired
    private  InitController initController;
    
    String token = "alumno#a@a.com.uy#a";
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString("");
    

    @Before
    public void before(){
        initController.newData();
        
    }
    
    @After
    public void after(){
        initController.borrarTodo();
    }
    
    @Test
    public void testFindAllPreguntaAlumno() throws Exception {

        try{        
            ResponseEntity<?> pregsAl = preguntaController.findAllPreguntaAlumno("");
            Assert.state(pregsAl.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se pudo obtener los preguntaAlumno sin el token.");
            
            pregsAl = preguntaController.findAllPreguntaAlumno(token);
            Assert.state(pregsAl.getStatusCode().equals(HttpStatus.OK), "Falló al traer los preguntaAlumno.");

        } catch(Exception e){
            Assert.state(false, "Error en testFindAllPreguntaAlumno");
        }
    }
    
    @Test
    public void testGetPreguntaAlumno() throws Exception {

        try{        
            ResponseEntity<Set<PreguntaAlumno>> pregsAl = (ResponseEntity<Set<PreguntaAlumno>>) preguntaController.findAllPreguntaAlumno(token);
            
            if (!pregsAl.getBody().isEmpty()) {
                Long id = null;
                for(PreguntaAlumno pa : pregsAl.getBody()) {
                    id = pa.getPreguntaId();
                    break;
                }
                preguntaController.getPreguntaAlumno(id, token);
            }
        } catch(Exception e){
            Assert.state(false, "Error en testGetPreguntaAlumno");
        }
    }
    
    @Test
    public void testResponderPregunta() throws Exception {

        try{        
            ResponseEntity<Set<PreguntaAlumno>> pregsAl = (ResponseEntity<Set<PreguntaAlumno>>) preguntaController.findAllPreguntaAlumno(token);
            
            if (!pregsAl.getBody().isEmpty()) {
                PreguntaAlumno p1 = new PreguntaAlumno();
                PreguntaAlumno p2 = new PreguntaAlumno();
                for(PreguntaAlumno pa : pregsAl.getBody()) {
                    if (!pa.isBlocked()) {
                        p1 = pa;
                    } else {
                        p2 = pa;
                    }
                }
                
                ResponseEntity<RespuestaPregunta> result = (ResponseEntity<RespuestaPregunta>) preguntaController.responderPregunta(p1.getPreguntaId(), p1.getPregunta().getRespuesta() + "Incorrecto", token);
                Assert.state(!result.getBody().isCorrecto(), "La respuesta debería ser incorrecta.");
                
                result = (ResponseEntity<RespuestaPregunta>) preguntaController.responderPregunta(p1.getPreguntaId(), p1.getPregunta().getRespuesta(), token);
                Assert.state(result.getBody().isCorrecto(), "La respuesta debería ser correcta.");
                
                try {
                    result = (ResponseEntity<RespuestaPregunta>) preguntaController.responderPregunta(p2.getPreguntaId(), p2.getPregunta().getRespuesta(), token);
                    Assert.state(result.getBody().isCorrecto(), "La pregunta está bloqueada no se debería poder responder.");
                } catch(Exception e1) {
                    Assert.state(true);
                }
                
            }
        } catch(Exception e){
            Assert.state(false, "Error en testResponderPregunta");
        }
    }
}