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
import calculus.backend.controller.v2.admin.PreguntaController;
import calculus.backend.controller.v2.admin.TemaController;
import calculus.backend.model.Informacion;
import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaCompletar;
import calculus.backend.model.Tema;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JpaConfig.class, CalculusBackendConfiguration.class})
public class AdminPreguntaControllerTest {
    @Autowired
    private PreguntaController preguntaController;
    @Autowired 
    private TemaController temaController;
    @Autowired
    private  InitController initController;
    
    String token = "profesor#admin@admin.com#admin";
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
    public void testCreate() throws Exception {

        try{
            
            ResponseEntity<List<Tema>> temas = temaController.findAll(token);
            Tema t1 = temas.getBody().get(0);
            
            Informacion descripcion;
            Informacion tip;
            List<String> opciones;
            PreguntaCompletar pc;
            
            PreguntaCompletar pc1;
            descripcion = new Informacion("Descripcion Test","");
            tip = new Informacion("Tip Test","");
            tip.setVideo("");
            pc1 = new PreguntaCompletar(t1);
            pc1.setId(null);
            pc1.setDescripcion(descripcion);
            pc1.setTip(tip);
            pc1.setPuntos(10);
            pc1.setRespuesta("1");
            
            pc1.setTitulo("Pregunta Test.");
            
            try {
                preguntaController.create(pc1, uriComponentsBuilder, "");
                Assert.state(false, "Se pudo crear pregunta sin el token.");
            } catch (Exception ex) {
               Assert.state(ex.getClass().equals(calculus.backend.exception.BadCredentialsException.class), "Tipo de excepción incorrecto."); 
            }
            
            ResponseEntity<?> result = preguntaController.create(pc1, uriComponentsBuilder, token);
            Assert.state(result.getStatusCode().equals(HttpStatus.BAD_REQUEST), "Se pudo crear pregunta con campos inválidos.");
            
            
            pc1.setInicial(true);
            pc1.setTitulo("Integral de e a la x.");
            result = preguntaController.create(pc1, uriComponentsBuilder, token);
            Assert.state(result.getStatusCode().equals(HttpStatus.FORBIDDEN), "Se pudo crear pregunta con titulo repetido.");
       
            pc1.setTitulo("Pregunta Test.");
            preguntaController.create(pc1, uriComponentsBuilder, token);
        
            ResponseEntity<List<Pregunta>> preguntas = preguntaController.findAll(token);
            Assert.state(preguntas.getBody().contains(pc1), "No se pudo crear la pregunta."); 
        }
        catch(Exception e){
            Assert.state(false, "Error en testCreate");
        }
    }
    
    @Test
    public void testUpdate() throws Exception {

        try{
            
            ResponseEntity<List<Pregunta>> preguntas = preguntaController.findAll(token);
            Pregunta preg = preguntas.getBody().get(0);
            
            try {
                preguntaController.update(preg, uriComponentsBuilder, "");
                Assert.state(false, "Se pudo actualizar la pregunta sin token.");
            } catch (Exception ex1) {
                Assert.state(ex1.getClass().equals(calculus.backend.exception.BadCredentialsException.class), "Tipo de excepción incorrecto."); 
            }
            
            preg.setPreguntasQueDesbloquea(null);
            preg.setPreguntasPrevias(null);
            preg.setInicial(false);
            
            ResponseEntity<Void> result = preguntaController.update(preg, uriComponentsBuilder, token);
            Assert.state(result.getStatusCode().equals(HttpStatus.BAD_REQUEST), "Se pudo actualizar la pregunta con campos inválidos.");
            
            preguntas = preguntaController.findAll(token);
            preg = preguntas.getBody().get(0);
            preg.setTitulo(preguntas.getBody().get(1).getTitulo());
            result = preguntaController.update(preg, uriComponentsBuilder, token);
            Assert.state(result.getStatusCode().equals(HttpStatus.FORBIDDEN), "Se pudo actualizar la pregunta con título repetido.");
            
            preg.setTitulo("Pregunta Test update.");
            preg.setPuntos(preg.getPuntos()+1);
            preguntaController.update(preg, uriComponentsBuilder, token);
            
            preguntas = preguntaController.findAll(token);
            Assert.state(preguntas.getBody().contains(preg) && preguntas.getBody().get(preguntas.getBody().indexOf(preg)).getTitulo().equals(preg.getTitulo()), "No se realizó correctamente el update.");

        }
        catch(Exception e){
            Assert.state(false, "Error en testUpdate");
        }
    }
    
    @Test
    public void testDelete() throws Exception {

        try{
            
            ResponseEntity<List<Pregunta>> preguntas = preguntaController.findAll(token);
            
            Long id1 = preguntas.getBody().get(0).getId();
            Long id2 = id1;
            Long id3 = id1;
            for (Pregunta p : preguntas.getBody()) {
                id1 = id1 + p.getId();
                if (!p.getPreguntasQueDesbloquea().isEmpty()) {
                    id2 = p.getId();
                } else if (id3.equals(preguntas.getBody().get(0).getId()) || !p.getPreguntasPrevias().isEmpty()){
                    id3 = p.getId();
                }
            }
            
            try {
                preguntaController.delete(id1, uriComponentsBuilder, token);
                Assert.state(false, "Se debería haber lanzado una excepción.");
            } catch (Exception e1) {
                Assert.state(e1.getClass().equals(javax.persistence.EntityNotFoundException.class), "La excepción no es la correcta.");
            }
            
            try {
                preguntaController.delete(id2, uriComponentsBuilder, token);
                Assert.state(false, "Se debería haber lanzado una excepción.");
            } catch (Exception e1) {
                Assert.state(e1.getClass().equals(calculus.backend.exception.EntityHasAssociationException.class), "La excepción no es la correcta.");
            }
            
            preguntaController.delete(id3, uriComponentsBuilder, token);
            try {
                preguntaController.findById(id3, token);
                Assert.state(false, "Se debería haber lanzado una excepción.");
            } catch (Exception e2) {
                Assert.state(true);
            }
            
        }
        catch(Exception e){
            Assert.state(false, "Error en testDelete");
        }
    }
}