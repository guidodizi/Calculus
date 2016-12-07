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
import calculus.backend.controller.v2.game.DudaController;
import calculus.backend.model.Alumno;
import calculus.backend.model.Duda;
import calculus.backend.model.Profesor;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
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
public class GameDudaControllerTest {
    @Autowired
    private DudaController dudaController;
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
    public void testFindAll() throws Exception {

        try{
            ResponseEntity<String> result = (ResponseEntity<String>) dudaController.findAll("");
            
            Assert.state(result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se pudo traer las dudas sin el token.");
            
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);
            
            Assert.state(dudas.getBody().size() > 0, "Se deberían haber encontrado dudas.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testFindAll.");
        }
        
    }
    
    @Test
    public void testFindById() throws Exception {

        try{
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);
            
            ResponseEntity<Duda> d = dudaController.findById("", dudas.getBody().get(1).getId());
            
            Assert.state(d.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se pudo traer obtener una duda por id, sin el token.");
            
            d = dudaController.findById(token, dudas.getBody().get(1).getId());
            
            Assert.state(d != null, "No se encontró la duda.");
            
            Long id = dudas.getBody().get(1).getId() + dudas.getBody().get(0).getId();
            
            d = dudaController.findById(token, id);
            
            Assert.state(d.getStatusCode().equals(HttpStatus.NOT_FOUND), "No se debería haber encontrado una duda.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testFindById.");
        }
        
    }
    
    @Test
    public void testCreate() throws Exception {
        
        try{
            Duda d;
            d = new Duda();
            d.setId(null);
            d.setDuda("creando duda en un test.");
            Date f = new Date(Calendar.getInstance().getTimeInMillis());
            d.setFecha(f);
            d.setLeida(false);
            //d.setAlumno(a);
            d.setTitulo("Test.");
            d.setMotivo("Test.");
            
            ResponseEntity<Void> result = dudaController.create("", d, uriComponentsBuilder);
            
            Assert.state(result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se permitió crear una duda sin el token.");
            
            dudaController.create(token, d, uriComponentsBuilder);
            
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);
            
            Assert.state(dudas.getBody().contains(d), "La duda creada no está en la base.");
        } catch (Exception e) {
            Assert.state(false, "Error en testCreate.");
        }
        
    }
    
    @Test
    public void testReplace() throws Exception {

        try{
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);

            Long id = dudas.getBody().get(1).getId();
            
            Duda d = dudas.getBody().get(1);
            d.setTitulo("Replace.");
            d.setMotivo("Replace.");
            
            dudaController.replace(token, d, uriComponentsBuilder);
            
            ResponseEntity<Duda> d2 = dudaController.findById(token, id);
            
            Assert.state(d2.getBody().getMotivo().equals("Replace."), "No se pudo reemplazar la duda.");
            
            d.setMotivo("Replace 2.");
            d.setTitulo("Replace 2.");
            ResponseEntity<Void> result = dudaController.replace("", d, uriComponentsBuilder);
            
            d2 = dudaController.findById(token, id);
            boolean condition = d2.getBody().getMotivo().equals("Replace.") && d2.getBody().getTitulo().equals("Replace.") && d2.getBody().getDuda().equals(d.getDuda()) && d2.getBody().getFecha().equals(d.getFecha());
            Assert.state( condition && result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se reemplazo la duda sin el token.");
            
        }
        catch(Exception e){
            Assert.state(false, "Error en testReplace");
        }

    }
}