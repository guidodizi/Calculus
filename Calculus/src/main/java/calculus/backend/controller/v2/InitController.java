package calculus.backend.controller.v2;

import calculus.backend.model.Error;
import calculus.backend.model.Alumno;
import calculus.backend.model.Duda;
import calculus.backend.model.Informacion;
import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaCompletar;
import calculus.backend.model.Profesor;
import calculus.backend.model.Tema;
import calculus.backend.service.AlumnoService;
import calculus.backend.service.DudaService;
import calculus.backend.service.ErrorService;
import calculus.backend.service.PreguntaService;
import calculus.backend.service.ProfesorService;
import calculus.backend.service.TemaService;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
    
    private static final Logger LOG = LoggerFactory.getLogger(InitController.class);
    
    @Autowired
    private AlumnoService alumnoService;
    
    @Autowired
    private PreguntaService preguntaService;
    
    @Autowired
    private TemaService temaService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private DudaService dudaService;
    
    @Autowired
    private ErrorService errorService;
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> init() {
        
        System.out.println("Hello World.");
        return new ResponseEntity<String>("API Running.", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ResponseEntity<String> newData() {
        
        System.out.println("Hello World.");
        LOG.info("Cargando datos de prueba ...");
        //Borro todo
        errorService.deleteAll();
        dudaService.deleteAll();
        alumnoService.deleteAll();
        profesorService.deleteAll();
        preguntaService.deleteAll();
        temaService.deleteAll();
        
        //no los borro porque funciona mal
        //Populo los Objectos de prueba
        
        //Temas
        Tema t1;
        Tema t2;
        
        t1 = new Tema();
        t1.setDescripcion("Usualmente las integrales se usan en el cálculo de áreas, longitudes de curvas y volúmenes de cuerpos de revolución.");
        t1.setTema("Integrales");
        temaService.create(t1);
        t2 = new Tema();
        t2.setDescripcion("Esta es una poderosa herramienta cuando comenzamos a pensar en la pendiente de una recta tangente a una curva");
        t2.setTema("Limites");
        temaService.create(t2);
        
        
        //PREGUNTAS
        //-------------------------
        Informacion descripcion;
        Informacion tip;
        List<String> opciones;
        PreguntaCompletar pc;
              
        //tema 1
        //-------------------------
        PreguntaCompletar pc1;//integral 5
        descripcion = new Informacion("Resuelva la siguiente integral:","http://c5.staticflickr.com/6/5613/30490740212_b07bfc659c_m.jpg");
        tip = new Informacion("Recuerda lo siguiente:","http://c6.staticflickr.com/6/5675/30607919165_4ae7eddc47_m.jpg");
        tip.setVideo("https://www.youtube.com/watch?v=oi3H4XxXk6o");
        pc1 = new PreguntaCompletar(t1);
        pc1.setDescripcion(descripcion);
        pc1.setTip(tip);
        pc1.setPuntos(1);
        //pc1.setRespuesta("4");
        pc1.setRespuesta("1");
        pc1.setInicial(true);
        pc1.setTitulo("Integral de e a la x.");
        preguntaService.create(pc1);
        
        PreguntaCompletar pc2;//integral 3
        descripcion = new Informacion("Escriba el resultado de la integral.","http://c7.staticflickr.com/6/5647/30490740142_4370bb38e7_m.jpg");
        tip = new Informacion("Recuerda lo siguiente:","http://c4.staticflickr.com/6/5670/30607919115_915ac3fa95_m.jpg");
        pc2 = new PreguntaCompletar(t1);
        pc2.setDescripcion(descripcion);
        pc2.setTip(tip);
        pc2.setPuntos(1);
        //pc2.setRespuesta("1/2");
        pc2.setRespuesta("1");
        pc2.setTitulo("x al cuadrado en el denominador.");
        pc2.getPreguntasPrevias().add(pc1.getId());
        preguntaService.create(pc2);
        
        PreguntaCompletar pc3;//integral 4
        descripcion = new Informacion("Escriba el resultado de la integral.","http://c5.staticflickr.com/6/5604/29976033404_e87fe2de29_m.jpg");
        tip = new Informacion("Recuerda la siguiente propiedad.","http://c7.staticflickr.com/6/5345/29976033534_431c32029f_m.jpg");
        pc3 = new PreguntaCompletar(t1);
        pc3.setDescripcion(descripcion);
        pc3.setTip(tip);
        pc3.setPuntos(1);
        //pc3.setRespuesta("1/6");
        pc3.setRespuesta("1");
        pc3.setTitulo("Integral de una resta.");
        pc3.getPreguntasPrevias().add(pc1.getId());
        preguntaService.create(pc3);
        
        PreguntaCompletar pc4;//integral 2
        descripcion = new Informacion("Escriba el resultado de la integral.","http://c4.staticflickr.com/6/5565/30519564331_3e32f447df_m.jpg");
        tip = new Informacion("Recuerda lo siguiente:","http://c7.staticflickr.com/6/5501/29976033494_0a3ccfca04_m.jpg");
        pc4 = new PreguntaCompletar(t1);
        pc4.setDescripcion(descripcion);
        pc4.setTip(tip);
        pc4.setPuntos(1);
        //pc4.setRespuesta("7/3");
        pc4.setRespuesta("1");
        pc4.setTitulo("Integral de x al cuadrado.");
        pc4.getPreguntasPrevias().add(pc2.getId());
        pc4.getPreguntasPrevias().add(pc3.getId());
        preguntaService.create(pc4);
        //-------------------------
        
        //tema 2
        //-------------------------
        PreguntaCompletar pc5;//limite 1
        descripcion = new Informacion("Resuelva el siguiente limite:","http://c8.staticflickr.com/6/5583/30607919095_6cc6875c40_m.jpg");
        tip = new Informacion("A medida que el denominador crece la el valor total se hace mas pequeño, piensa a cuanto tiende este limite.","http://c8.staticflickr.com/6/5650/30519564591_3b6ac8f022.jpg");
        pc5 = new PreguntaCompletar(t2);
        pc5.setDescripcion(descripcion);
        pc5.setTip(tip);
        pc5.setPuntos(1);
        pc5.setRespuesta("1");
        pc5.setInicial(true);
        pc5.setTitulo("Limite con denominador tendiendo a infinito.");
        preguntaService.create(pc5);
        
        
        //-------------------------
        
        //ALUMNOS
        //-------------------------
        Alumno a;
        
        a = new Alumno();
        a.setEmail("a@a.com.uy");
        a.setNombre("Andres Gonzalez");
        a.setPassword("a");
        a.setPuntaje(10);
        alumnoService.create(a);
        alumnoService.asignarPreguntasAlAlumno(a.getId());
        
        Alumno b;
        
        b = new Alumno();
        b.setEmail("b@b.com");
        b.setNombre("Brian Rodriguez");
        b.setPassword("b");
        b.setPuntaje(20);
        
        alumnoService.create(b);
        alumnoService.asignarPreguntasAlAlumno(b.getId());
        
        Alumno a2;
        
        a2 = new Alumno();
        a2.setEmail("c@c.com.uy");
        a2.setNombre("Carlos Gutierrez");
        a2.setPassword("c");
        a2.setPuntaje(60);
        alumnoService.create(a2);
        alumnoService.asignarPreguntasAlAlumno(a2.getId());
        
        Alumno a3;
        
        a3 = new Alumno();
        a3.setEmail("d@d.com.uy");
        a3.setNombre("Dario Perez");
        a3.setPassword("d");
        a3.setPuntaje(40);
        alumnoService.create(a3);
        alumnoService.asignarPreguntasAlAlumno(a3.getId());
        
        Alumno a4;
        
        a4 = new Alumno();
        a4.setEmail("e@e.com.uy");
        a4.setNombre("Esteban Hernandez");
        a4.setPassword("e");
        a4.setPuntaje(100);
        alumnoService.create(a4);
        alumnoService.asignarPreguntasAlAlumno(a4.getId());
        //Respondo una pregunta a modo de ejemplo.
        /*try {
        System.out.println("Pregunta: 53");
        if(alumnoService.responderPregunta(a.getId(),53L, "Hola")) {
        System.out.println("Respndi Bien");
        } else {
        System.out.println("Respndi Mal.");
        }
        //-------------------------
        } catch (Exception ex) {
        System.out.println("El alumno no tiene la pregunta o algun otro error");
        Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        System.out.println("Pregunta: 4");
        if(alumnoService.responderPregunta(a.getId(),4L, "opc2_p1")) {
        System.out.println("Respndi Bien");
        } else {
        System.out.println("Respndi Mal.");
        }
        if(alumnoService.responderPregunta(a.getId(),4L, "opc2_p2")) {
        System.out.println("Respndi Bien");
        } else {
        System.out.println("Respndi Mal.");
        }
        //-------------------------
        } catch (Exception ex) {
        System.out.println("El alumno no tiene la pregunta o algun otro error");
        Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
        System.out.println("Pregunta: 5");
        if(alumnoService.responderPregunta(a.getId(),5L, "BLABLA")) {
        System.out.println("Respndi Bien");
        } else {
        System.out.println("Respndi Mal.");
        }
        if(alumnoService.responderPregunta(a.getId(),5L, "15")) {
        System.out.println("Respndi Bien");
        } else {
        System.out.println("Respndi Mal.");
        }
        //-------------------------
        } catch (Exception ex) {
        System.out.println("El alumno no tiene la pregunta o ya la habia respuesto");
        Logger.getLogger(InitController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
//        
        Profesor profe;
        
        profe = new Profesor();
        profe.setEmail("admin@admin.com");
        profe.setNombre("Administrador");
        profe.setPassword("admin");
        Map<Long,Pregunta> pregC;
        pregC = new HashMap<Long,Pregunta>();
        pregC.put(pc5.getId(), pc5);
        profe.setPreguntasCreadas(pregC);
        pc5.setProfesor(profe);
        profesorService.create(profe);
        
        //-------------------------
        
//        Alumno c;
//
//        c = new Alumno();
//        c.setEmail("c@c.com.uy");
//        c.setNombre("c");
//        c.setPassword("c");
//        c.setPuntaje(10);
//        c.setToken_key("token_c");
//        service.create(c);

//-------------------------
//Dudas
        Duda d;
        d = new Duda();
        d.setDuda("La integral de e a la x da como resultado e a la x, pero si en vez de x tengo 3x? la integral de e a la 3x es e a la 3x?");
        Date f = new Date(Calendar.getInstance().getTimeInMillis());
        d.setFecha(f);
        d.setLeida(false);
        d.setAlumno(a);
        d.setTitulo("Integrando e.");
        d.setMotivo("Integrando e.");
        dudaService.create(d);

        d = new Duda();
        d.setDuda("Como se resuelve una integral con la variable a integrar en el exponencial? ejemplo: 3 a la x.");
        f.setMonth(8);
        d.setFecha(f);
        d.setLeida(false);
        d.setAlumno(a);
        d.setTitulo("Integral con variable en el exponencial.");
        d.setMotivo("Integral con variable en el exponencial.");
        dudaService.create(d);

        d = new Duda();
        d.setDuda("Que sucede si tengo Limite de (x / e a la x) con x tendiendo a infinito? tanto numerador como denominador tienden a infinito.");
        f.setMonth(7);
        d.setFecha(f);
        d.setLeida(false);
        d.setAlumno(b);
        d.setTitulo("Limite infinito sobre infinito.");
        d.setMotivo("Limite infinito sobre infinito.");
        dudaService.create(d);
        
//-------------------------
//Errores
        Error error1 = new Error(a,pc1,"La imagen de la pregunta no se ve.");
        errorService.create(error1);
        
        Error error2 = new Error(a,pc2,"La pregunta esta mal planteada.");
        errorService.create(error2);
        
        Error error3 = new Error(b,pc1,"La imagen de la pregunta no se ve.");
        errorService.create(error3);
        
//-------------------------
        LOG.info("Datos de prueba cargados ...");
        System.out.println("Datos Cargados");
        return new ResponseEntity<String>("Datos Cargados.", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/newOnlyProf", method = RequestMethod.GET)
    public ResponseEntity<String> newOnlyProf() {
        
        LOG.info("Cargando datos de prueba solo profesor ...");
        //Borro todo
        errorService.deleteAll();
        dudaService.deleteAll();
        alumnoService.deleteAll();
        profesorService.deleteAll();
        preguntaService.deleteAll();
        temaService.deleteAll();
        
        Profesor profe;
        
        profe = new Profesor();
        profe.setEmail("admin@admin.com");
        profe.setNombre("Administrador");
        profe.setPassword("admin");
        Map<Long,Pregunta> pregC;
        pregC = new HashMap<Long,Pregunta>();
        profe.setPreguntasCreadas(pregC);
        profesorService.create(profe);
        
        LOG.info("Datos de prueba cargados ...");
        
        return new ResponseEntity<String>("Datos Cargados.", HttpStatus.OK);
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Void> login(){
        if(true){
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
    };
    
    public void borrarTodo(){
        errorService.deleteAll();
        dudaService.deleteAll();
        alumnoService.deleteAll();
        profesorService.deleteAll();
        preguntaService.deleteAll();
        temaService.deleteAll();
    }
}