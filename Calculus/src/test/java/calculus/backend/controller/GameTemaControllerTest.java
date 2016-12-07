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
import calculus.backend.controller.v2.game.TemaController;
import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.Tema;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
public class GameTemaControllerTest {
    @Autowired
    private TemaController temaController;
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
            ResponseEntity<Collection<Tema>> temas = (ResponseEntity<Collection<Tema>>) temaController.findAllPreguntaAlumno(token);
            Assert.state(temas.getBody().size() > 0, "No se encontraron temas, deberían haber.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testFindAllPreguntaAlumno.");
        }
        
    }
    
    @Test
    public void testGetPreguntasAlumno() throws Exception {

        try{
            ResponseEntity<Collection<Tema>> temas = (ResponseEntity<Collection<Tema>>) temaController.findAllPreguntaAlumno(token);
            Long id = null;
            for(Tema t: temas.getBody()) {
                id = t.getId();
            }
            
            ResponseEntity<Void> result = (ResponseEntity<Void>) temaController.getPreguntasAlumno("", id);
            Assert.state(result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Debería haber lanzado una excepción del tipo unauthorized");
            
            ResponseEntity<Set<PreguntaAlumno>> preguntasAlumno =  (ResponseEntity<Set<PreguntaAlumno>>) temaController.getPreguntasAlumno(token, id);
            Assert.state(true);
        }
        catch(Exception e){
            Assert.state(false, "Error en testFindAllPreguntaAlumno.");
        }
        
    }
    
}