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
import calculus.backend.controller.v2.admin.TemaController;
import calculus.backend.model.Pregunta;
import calculus.backend.model.Tema;
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
public class AdminTemaControllerTest {
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
    public void testFindAll() throws Exception {

        try{
            ResponseEntity<List<Tema>> temas = temaController.findAll(token);
            Assert.state(temas.getBody().size() > 0, "No se encontraron temas, deberían haber.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testFindAll.");
        }
        
    }
    
    @Test
    public void testGetPreguntasAlumnoTema() throws Exception {

        try{
            ResponseEntity<List<Tema>> temas = temaController.findAll(token);
            
            Assert.state(temas.getBody().size() > 0, "No se encontraron temas, deberían haber.");
            
            Long idTema = temas.getBody().get(0).getId();
            
            ResponseEntity<Set<Pregunta>> preguntasAlumnoTema = (ResponseEntity<Set<Pregunta>>) temaController.getPreguntasAlumnoTema(idTema, token);
            
            Assert.state(!preguntasAlumnoTema.getBody().isEmpty(), "No se encontraron preguntasAlumno en el tema.");
            
        }
        catch(Exception e){
            Assert.state(false, "Error en testGetPreguntasAlumnoTema.");
        }
        
    }
    
    @Test
    public void testCreate() throws Exception {

        try{
            ResponseEntity<List<Tema>> temas = temaController.findAll(token);
            
            Assert.state(temas.getBody().size() > 0, "No se encontraron temas, deberían haber.");
            
            Tema t1;
            
            t1 = new Tema();
            t1.setDescripcion("Usualmente las integrales se usan en el cálculo de áreas, longitudes de curvas y volúmenes de cuerpos de revolución.");
            t1.setTema("Integrales");
            ResponseEntity<?> result = temaController.create(t1, uriComponentsBuilder, token);
            
            Assert.state(result.getStatusCode().equals(HttpStatus.FORBIDDEN), "Se creó un tema con titulo existente.");
            
            Tema t2;
            t2 = new Tema();
            t2.setDescripcion("Descripcion del nuevo tema.");
            t2.setTema("Nuevo Tema");
            temaController.create(t2, uriComponentsBuilder, token);
            
            temas = temaController.findAll(token);
            
            Assert.state(temas.getBody().contains(t2), "No se encontro el nuevo en el tema.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testCreate.");
        }
    }
    
    @Test
    public void testUpdate() throws Exception {

        try{
            ResponseEntity<List<Tema>> temas = temaController.findAll(token);
            
            Assert.state(temas.getBody().size() > 0, "No se encontraron temas, deberían haber.");
            
            Tema t1 = temas.getBody().get(0);
            t1.setTema("Limites");
            t1.setDescripcion("Tema update descripción.");
            ResponseEntity<Void> result = temaController.update(t1, uriComponentsBuilder, token);
            Assert.state(result.getStatusCode().equals(HttpStatus.FORBIDDEN), "No se debería haber hecho el update del tema ya que se repite el titulo.");
            
            t1.setTema("Tema update");
            temaController.update(t1, uriComponentsBuilder, token);
            ResponseEntity<Tema> tema = temaController.findById(t1.getId(), token);
            boolean condition = tema.getBody().getTema().equals(t1.getTema()) && tema.getBody().getDescripcion().equals(t1.getDescripcion());
            
            Assert.state(condition, "No se realizo el update del tema.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testUpdate.");
        }
    }
    
    @Test
    public void testDelete() throws Exception {

        try{
            ResponseEntity<List<Tema>> temas = temaController.findAll(token);
            
            Assert.state(temas.getBody().size() > 0, "No se encontraron temas, deberían haber.");
            
            Tema t1 = temas.getBody().get(0);
            Long id = t1.getId();
            
            for(Tema t: temas.getBody()) {
                id = id + t.getId();
            }
            
            ResponseEntity<Void> result;
            try {
                result = temaController.delete(id, uriComponentsBuilder, token);
                Assert.state(false,"Debería haber lanzado una excepción.");
            } catch (Exception e) {
                Assert.state(e.getClass().equals(javax.persistence.EntityNotFoundException.class), "La exceptión no es la correcta.");
            }
            
            try {
                result = temaController.delete(t1.getId(), uriComponentsBuilder, token);
                Assert.state(false,"Debería haber lanzado una excepción.");
            } catch(Exception e) {
                Assert.state(e.getClass().equals(calculus.backend.exception.EntityHasAssociationException.class), "La exceptión no es la correcta.");
            }
            
            Tema t2;
            t2 = new Tema();
            t2.setId(null);
            t2.setDescripcion("Descripcion delete tema.");
            t2.setTema("Delete Tema");
            temaController.create(t2, uriComponentsBuilder, token);
            
            temas = temaController.findAll(token);
            
            for (Tema t: temas.getBody()) {
                if (t.getTema().equals(t2.getTema()) && t.getDescripcion().equals(t2.getDescripcion())) {
                    id = t.getId();
                }
            }
            
            temaController.delete(id, uriComponentsBuilder, token);
            try {
                ResponseEntity<Tema> lastResult = temaController.findById(id, token);
                Assert.state(false, "Debería haber lanzado una excepción.");
            } catch (Exception e) {
                Assert.state(true);
            }
        }
        catch(Exception e){
            Assert.state(false, "Error en testDelete.");
        }
    }
}
