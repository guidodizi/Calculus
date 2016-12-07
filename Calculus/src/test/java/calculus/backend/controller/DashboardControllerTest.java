/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller;

import calculus.backend.CalculusBackendConfiguration;
import calculus.backend.JpaConfig;
import calculus.backend.controller.v2.InitController;
import calculus.backend.controller.v2.admin.AlumnoController;
import calculus.backend.controller.v2.admin.DashboardController;
import calculus.backend.controller.v2.admin.PreguntaController;
import calculus.backend.controller.v2.admin.TemaController;
import calculus.backend.model.Alumno;
import calculus.backend.model.EstadisticasDashboard;
import calculus.backend.model.Informacion;
import calculus.backend.model.PreguntaCompletar;
import calculus.backend.model.Tema;
import java.util.List;
import junit.framework.Assert;
import static org.junit.Assert.assertEquals;
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
public class DashboardControllerTest {
    @Autowired
    private DashboardController dashboardController;
    @Autowired
    private AlumnoController alumnoController;
    @Autowired
    private PreguntaController preguntaController;
    @Autowired
    private TemaController temaController;
    @Autowired
    private  InitController initController;
    
    String token = "profesor#admin@admin.com#admin";
    
    public UriComponentsBuilder getUCBuilder(){
        return UriComponentsBuilder.fromUriString("");
    }

    @Before
    public void before(){
        initController.newOnlyProf();
        
    }
    
    
    @Test
    public void testEstadisticas(){
        Alumno a1 = new Alumno();
        a1.setNombre("nombre1");
        a1.setEmail("test1@test.com");
        a1.setPassword("pass");
        a1.setPuntaje(10);
        alumnoController.create(a1, UriComponentsBuilder.fromUriString(""), token);
        
        Alumno a2 = new Alumno();
        a2.setNombre("nombre2");
        a2.setEmail("test2@test.com");
        a2.setPassword("pass");
        a2.setPuntaje(5);
        alumnoController.create(a2, UriComponentsBuilder.fromUriString(""), token);
        
        Alumno a3 = new Alumno();
        a3.setNombre("nombre3");
        a3.setEmail("test3@test.com");
        a3.setPassword("pass");
        a3.setPuntaje(20);
        alumnoController.create(a3, UriComponentsBuilder.fromUriString(""), token);
        
        ResponseEntity<EstadisticasDashboard> response = dashboardController.getEstadisticas();
       EstadisticasDashboard estadisticas = (EstadisticasDashboard) response.getBody();
        
        assertEquals(3L,estadisticas.getCantidadAlumnos());
        assertEquals(0L,estadisticas.getCantidadDudas());
        assertEquals(0L,estadisticas.getCantidadRespuestas());
        
    }
        
    
}
