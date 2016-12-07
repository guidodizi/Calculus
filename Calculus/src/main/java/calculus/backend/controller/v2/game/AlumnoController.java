/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2.game;


import calculus.backend.model.Alumno;
import calculus.backend.service.AlumnoService;
import calculus.backend.service.SessionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mariano
 */
@RestController("v2GameAlumnoController")
@RequestMapping(value = AlumnoController.URI_PREFIX)
public class AlumnoController {

    public static final String URI_PREFIX = "/v2/game/alumnos";
    
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    SessionService sessionService;
    
    @RequestMapping(value = "/ranking", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllPreguntaAlumno(@RequestHeader(value="Authorization", required = false) String token_key) {
        Alumno user;
        user = (Alumno) sessionService.login(token_key);
            
        
        List<Alumno> alumnos =  alumnoService.getRanking();
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

        for (Alumno a: alumnos) {
            Map<String, Object> obj = new HashMap<String, Object>();
            obj.put("nombre", a.getNombre());
            obj.put("puntaje", a.getPuntaje());
            obj.put("id",a.getId());
            result.add(obj);
        }
        
        return new ResponseEntity<List<Map<String,Object>>>(result,HttpStatus.OK);
        
    }
    
    
}

