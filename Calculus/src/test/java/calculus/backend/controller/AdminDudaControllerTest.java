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
import calculus.backend.controller.v2.admin.DudaController;
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
public class AdminDudaControllerTest {
    @Autowired
    private DudaController dudaController;
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
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll("");
            
            Assert.state(dudas.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se pudo traer las dudas sin el token.");
            
            dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);
            
            Assert.state(dudas.getBody().size() == 3);
            
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
            
            Long id = dudas.getBody().get(1).getId() + dudas.getBody().get(0).getId() + dudas.getBody().get(2).getId();
            
            d = dudaController.findById(token, id);
            
            Assert.state(d.getStatusCode().equals(HttpStatus.NOT_FOUND), "No se debería haber encontrado una duda.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testFindById.");
        }
        
    }
    
    @Test
    public void testSetRespuesta() throws Exception {

        try{
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);

            Long id = dudas.getBody().get(1).getId();
            
            dudaController.setRespuesta(token, id, "Respeusta Test 1", uriComponentsBuilder);
            
            ResponseEntity<Duda> d = dudaController.findById(token, id);

            Assert.state(d.getBody().getRespuesta().equals("Respeusta Test 1"), "No se pudo setear la respuesta de la duda.");
            
            dudaController.setRespuesta(token, id, "Respeusta Test 2", uriComponentsBuilder);
            
            d = dudaController.findById(token, id);
            
            Assert.state(d.getBody().getRespuesta().equals("Respeusta Test 1"), "Se pudo setear 2 veces la respuesta de la misma duda.");
            
            id = dudas.getBody().get(0).getId();
            
            ResponseEntity<Void> result = dudaController.setRespuesta("", id, "Respeusta Test 3", uriComponentsBuilder);
            
            Assert.state(result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se pudo setear la respuesta de la duda sin token.");
            
            id = dudas.getBody().get(1).getId() + dudas.getBody().get(0).getId() + dudas.getBody().get(2).getId();
            
            result = dudaController.setRespuesta(token, id, "Respeusta Test 4", uriComponentsBuilder);
            
            Assert.state(result.getStatusCode().equals(HttpStatus.NOT_FOUND), "No se debería haber encontrado una duda.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testSetRespuesta.");
        }

    }
    
    
     @Test
    public void testSetLeida() throws Exception {

        try{
            ResponseEntity<List<Duda>> dudas = (ResponseEntity<List<Duda>>) dudaController.findAll(token);

            Long id = dudas.getBody().get(1).getId();
            
            dudaController.setLeida(token, id, true, uriComponentsBuilder);
            
            ResponseEntity<Duda> d = dudaController.findById(token, id);

            Assert.state(d.getBody().getLeida(), "No se pudo setear el parametro leida de la duda.");
            
            ResponseEntity<Void> result = dudaController.setLeida("", id, true, uriComponentsBuilder);
            
            Assert.state(result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se pudo setear el parametro leida de la duda sin token.");
            
            id = dudas.getBody().get(1).getId() + dudas.getBody().get(0).getId() + dudas.getBody().get(2).getId();
            
            result = dudaController.setLeida(token, id, true, uriComponentsBuilder);
            
            Assert.state(result.getStatusCode().equals(HttpStatus.NOT_FOUND), "No se debería haber encontrado una duda.");
            
        }
        catch(Exception e){
            Assert.state(false, "Error en testSetLeida");
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
            
            Assert.state(d2.getBody().getMotivo().equals("Replace.") && result.getStatusCode().equals(HttpStatus.UNAUTHORIZED), "Se reemplazo la duda sin el token.");

        }
        catch(Exception e){
            Assert.state(false, "Error en testSetLeida");
        }

    }
}