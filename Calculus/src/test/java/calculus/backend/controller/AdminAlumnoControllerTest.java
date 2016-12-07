/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller;

/**
 *
 * @author marti
 */
import calculus.backend.CalculusBackendConfiguration;
import calculus.backend.JpaConfig;
import calculus.backend.controller.v2.InitController;
import calculus.backend.controller.v2.admin.AlumnoController;
import calculus.backend.controller.v2.admin.PreguntaController;
import calculus.backend.controller.v2.admin.TemaController;
import calculus.backend.model.Alumno;
import calculus.backend.model.Informacion;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.PreguntaCompletar;
import calculus.backend.model.Profesor;
import calculus.backend.model.Tema;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminAlumnoControllerTest {
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
    public void testMailDuplicado() throws Exception {
        
        Alumno a = new Alumno();
        a.setNombre("nombre");
        a.setEmail("test@test.com");
        a.setPassword("pass");
        alumnoController.create(a, UriComponentsBuilder.fromUriString(""), token);
        
        a = new Alumno();
        a.setNombre("nombre");
        a.setEmail("test@test.com");
        a.setPassword("pass");
        try{
            alumnoController.create(a, UriComponentsBuilder.fromUriString(""), token);
            Assert.state(false, "Se crearon dos alumnos con el mismo mail");
        }
        catch(Exception e){
            
        }
        
    }
    
    @Test
    public void testRanking(){
        
        
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
        
        //Ranking general
        //------------------------------------------------------------
        ResponseEntity<List<Map<String,Object>>> response =  (ResponseEntity<List<Map<String,Object>>>) alumnoController.getRanking(token);
        
        List<Map<String,Object>> ranking = response.getBody();
        
        String nombre;
        nombre = (String) ranking.get(0).get("nombre");
        assertEquals(nombre, "nombre3");
        
        nombre = (String) ranking.get(1).get("nombre");
        assertEquals(nombre, "nombre1");
        
        nombre = (String) ranking.get(2).get("nombre");
        assertEquals(nombre, "nombre2");
        //------------------------------------------------------------
        
        
        //Ranking por alumno
        //------------------------------------------------------------
        ResponseEntity<Map<String,Object>> response1;
        Map<String,Object> alumnoRanking;
        
        response1 = (ResponseEntity<Map<String,Object>>) alumnoController.getRankingAlumno(token, a1.getId());
        alumnoRanking = response1.getBody();
        assertEquals(alumnoRanking.get("posicion"), 2);
        
        response1 = (ResponseEntity<Map<String,Object>>) alumnoController.getRankingAlumno(token, a2.getId());
        alumnoRanking = response1.getBody();
        assertEquals(alumnoRanking.get("posicion"), 3);
        
        response1 = (ResponseEntity<Map<String,Object>>) alumnoController.getRankingAlumno(token, a3.getId());
        alumnoRanking = response1.getBody();
        assertEquals(alumnoRanking.get("posicion"), 1);
        //------------------------------------------------------------
        
        
        
        
        
        
        
    }
    
    @Test
    public void testProgreso(){
        Informacion descripcion;
        Informacion tip;
        List<String> opciones;
        PreguntaCompletar pc;
        
        Tema t1 = new Tema();
        t1.setDescripcion("Usualmente las integrales se usan en el cálculo de áreas, longitudes de curvas y volúmenes de cuerpos de revolución.");
        t1.setTema("Integrales");
        temaController.create(t1, getUCBuilder(), token);
        
        PreguntaCompletar pc1;//integral 5
        descripcion = new Informacion("Resuelva la siguiente integral:","http://c5.staticflickr.com/6/5613/30490740212_b07bfc659c_m.jpg");
        tip = new Informacion("Recuerda lo siguiente:","http://c6.staticflickr.com/6/5675/30607919165_4ae7eddc47_m.jpg");
        tip.setVideo("https://www.youtube.com/watch?v=oi3H4XxXk6o");
        pc1 = new PreguntaCompletar(t1);
        pc1.setDescripcion(descripcion);
        pc1.setTip(tip);
        pc1.setPuntos(1);
        pc1.setRespuesta("1");
        pc1.setInicial(true);
        pc1.setTitulo("Integral de e a la x.");
        preguntaController.create(pc1, getUCBuilder(), token);
        
        Alumno a1 = new Alumno();
        a1.setNombre("nombre1");
        a1.setEmail("test1@test.com");
        a1.setPassword("pass");
        a1.setPuntaje(10);
        alumnoController.create(a1, UriComponentsBuilder.fromUriString(""), token);
        
        ResponseEntity response = alumnoController.getProgresoAlumno(token, a1.getId());
        Set<PreguntaAlumno> progreso = (Set<PreguntaAlumno>) response.getBody();
        
        for (PreguntaAlumno preguntaAlumno : progreso){
            Assert.isTrue(!preguntaAlumno.fueRespondida(),"Esta pregunta no deberia estar marcada como respondida");
            Assert.isTrue(!preguntaAlumno.isBlocked(),"Esta pregunta  deberia estar debloqueada");
        }
        
    }
}