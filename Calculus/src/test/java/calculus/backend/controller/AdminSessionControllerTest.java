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
import calculus.backend.controller.v2.admin.SessionController;
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
public class AdminSessionControllerTest {
    @Autowired
    private SessionController sessionController;
    @Autowired
    private  InitController initController;
        

    @Before
    public void before(){
        initController.newData();
        
    }
    
    @After
    public void after(){
        initController.borrarTodo();
    }
    
    
    @Test
    public void testLogin() throws Exception {

        try{
            String email = "admin@admin.com";
            ResponseEntity<?> result = sessionController.login("profesor", email, "admin");
                        
            Assert.state(result != null, "No se encontro el nuevo usuario.");
        }
        catch(Exception e){
            Assert.state(false, "Error en testLogin.");
        }
    }
    

    

    
}
