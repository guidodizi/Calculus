/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller;

import calculus.backend.CalculusBackendConfiguration;
import calculus.backend.JpaConfig;
import calculus.backend.controller.v2.InitController;
import calculus.backend.controller.v2.game.AlumnoController;
import calculus.backend.model.Alumno;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author marti
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {JpaConfig.class, CalculusBackendConfiguration.class})
public class GameAlumnoControllerTest {
    @Autowired
    private AlumnoController alumnoController;
    @Autowired
    private calculus.backend.controller.v2.admin.AlumnoController adminAlumnoController;
    @Autowired
    private  InitController initController;
    
    String token = "profesor#admin@admin.com#admin";
    String tokenAlumno = "alumno#test1@test.com#pass";
    
    public UriComponentsBuilder getUCBuilder(){
        return UriComponentsBuilder.fromUriString("");
    }

    @Before
    public void before(){
        initController.newOnlyProf();
        
    }
    
    @Test
    public void testRanking(){
        Alumno a1 = new Alumno();
        a1.setNombre("nombre1");
        a1.setEmail("test1@test.com");
        a1.setPassword("pass");
        a1.setPuntaje(10);
        adminAlumnoController.create(a1, UriComponentsBuilder.fromUriString(""), token);
        
        Alumno a2 = new Alumno();
        a2.setNombre("nombre2");
        a2.setEmail("test2@test.com");
        a2.setPassword("pass");
        a2.setPuntaje(5);
        adminAlumnoController.create(a2, UriComponentsBuilder.fromUriString(""), token);
        
        Alumno a3 = new Alumno();
        a3.setNombre("nombre3");
        a3.setEmail("test3@test.com");
        a3.setPassword("pass");
        a3.setPuntaje(20);
        adminAlumnoController.create(a3, UriComponentsBuilder.fromUriString(""), token);
        
        ResponseEntity response = alumnoController.findAllPreguntaAlumno(tokenAlumno);
        List<Map<String,Object>> ranking = (List<Map<String,Object>>) response.getBody();
        
        Assert.assertEquals("nombre3",ranking.get(0).get("nombre"));
        Assert.assertEquals("nombre1",ranking.get(1).get("nombre"));
        Assert.assertEquals("nombre2",ranking.get(2).get("nombre"));
        
    }
        
    
}
