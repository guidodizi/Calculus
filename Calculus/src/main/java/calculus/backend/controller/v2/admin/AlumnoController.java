package calculus.backend.controller.v2.admin;

import calculus.backend.model.Alumno;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.Profesor;
import calculus.backend.service.AbstractService;
import calculus.backend.service.AlumnoService;
import calculus.backend.service.SessionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("v2AdminAlumnoController")
@RequestMapping(value = AlumnoController.URI_PREFIX)
public class AlumnoController extends AbstractController<Alumno>{

    public static final String URI_PREFIX = "/v2/admin/alumnos";
    
    @Autowired
    protected AlumnoService service;
    @Autowired
    protected SessionService sessionService;
    
    @Override
    protected AbstractService getService() {
        return this.service;
    }
    
    @Override
    protected String getMapString() {
        return URI_PREFIX;
    }

    @Override
    protected void checkCreate() {
    }

    @Override
    protected void checkUpdate() {
    }

    @Override
    protected void checkDelete() {
    }

    @RequestMapping(value = "/{id}/progreso", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProgresoAlumno( @RequestHeader(value="Authorization", required = false) String token_key,
        @PathVariable("id") Long id) {
        
        Profesor prof = (Profesor) sessionService.login(token_key);
        
        try {   
            Set<PreguntaAlumno> list = service.getPreguntasAlumno(id);
            
            if(list==null){
                return new ResponseEntity<Set<PreguntaAlumno>>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<Set<PreguntaAlumno>>(list,HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<Set<PreguntaAlumno>>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/ranking", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRanking(@RequestHeader(value="Authorization", required = false) String token_key) {
        Profesor prof = (Profesor) sessionService.login(token_key);
        
        List<Alumno> alumnos =  service.getRanking();
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        
        int posicion = 1;
        for (Alumno a: alumnos) {
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("nombre", a.getNombre());
            obj.put("email", a.getEmail());
            obj.put("puntaje", a.getPuntaje());
            obj.put("id",a.getId());
            obj.put("posicion", posicion);
            result.add(obj);
            posicion++;
        }
        
        return new ResponseEntity<List<Map<String,Object>>>(result,HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/{id}/ranking", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRankingAlumno(@RequestHeader(value="Authorization", required = false) String token_key,
            @PathVariable("id") Long id) {
        Profesor prof;
        Alumno user;
        prof = (Profesor) sessionService.login(token_key);
        
        
        try {
            user = service.findById(id);
            
            List<Alumno> alumnos =  service.getRanking();
            int cantAlumnos = alumnos.size();
            Map<String, Object> obj = new HashMap<String, Object>();
            int pos = alumnos.indexOf(user)+1;
            
            obj.put("nombre", user.getNombre());
            obj.put("email", user.getEmail());
            obj.put("puntaje", user.getPuntaje());
            obj.put("id",user.getId());
            obj.put("posicion", pos);
            obj.put("cantAlumnos", cantAlumnos);
            obj.put("respuestasIncorrectas", user.getCantidadPreguntasIncorrectas());
            obj.put("respuestasCorrectas", user.getCantidadPreguntasCorrectas());
            
            return new ResponseEntity<Map<String,Object>>(obj,HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
    
}