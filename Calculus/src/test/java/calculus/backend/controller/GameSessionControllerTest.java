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
import calculus.backend.controller.v2.game.SessionController;
import calculus.backend.model.Alumno;
import calculus.backend.service.AlumnoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JpaConfig.class, CalculusBackendConfiguration.class})
public class GameSessionControllerTest {
    @Autowired
    private SessionController sessionController;
    @Autowired
    private  InitController initController;
    @Autowired
    private  AlumnoService alumnoService;
        

    @Before
    public void before(){
        initController.newData();
        
    }
    
    @After
    public void after(){
        initController.borrarTodo();
    }
    
    
    @Test
    public void testRegistrar() throws Exception {

        try{
            String email = "jacoco@mail.com";
            ResponseEntity<?> result = sessionController.registrar(email, "jack", "1234");
            
            Alumno usuario = alumnoService.findByEmail(email);
            
            Assert.state(usuario != null, "No se encontro el nuevo usuario.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testRegistrar.");
        }
    }
    
    @Test
    public void testFbLogin() throws Exception {

        try{
            Long fbId = 567L;
            ResponseEntity<?> result = sessionController.fbLogin(fbId, "fbNuevo");
            
            Alumno usuario = alumnoService.findByEmail(fbId.toString()+"@facebook.com");
            
            Assert.state(usuario != null, "No se encontro el nuevo usuario.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testFbLogin.");
        }
    }
    
    @Test
    public void testLogin() throws Exception {

        try{
            String email = "a@a.com.uy";
            ResponseEntity<?> result = sessionController.login("alumno", email, "a");
            
            Alumno usuario = alumnoService.findByEmail(email);
            
            Assert.state(usuario != null, "No se encontro el nuevo usuario.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testLogin.");
        }
    }
    
}
