/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2.game;

import calculus.backend.model.Alumno;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.RespuestaPregunta;
import calculus.backend.service.AlumnoService;
import calculus.backend.service.PreguntaService;
import calculus.backend.service.SessionService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author marti
 */
@RestController("v2GamePreguntaController")
@RequestMapping(value = "/v2/game/preguntas")
public class PreguntaController {
    @Autowired
    PreguntaService preguntaService;
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    SessionService sessionService;
    
    
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllPreguntaAlumno(@RequestHeader(value="Authorization", required = false) String token_key) {
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
            
        } catch (Exception ex) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Set<PreguntaAlumno>>(alumnoService.getPreguntasAlumno(user.getId()),HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPreguntaAlumno(
            @PathVariable("id") long pregunta_id,
            @RequestHeader(value="Authorization", required = false) String token_key
    ) {
        Alumno user;
            user = (Alumno) sessionService.login(token_key);
            return new ResponseEntity<PreguntaAlumno>(alumnoService.getPreguntaAlumno(user.getId(),pregunta_id),HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/{id}/respuesta", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> responderPregunta(
            @PathVariable("id") long pregunta_id,
            @RequestParam(value="r") String respuesta,
            @RequestHeader(value="Authorization", required = false) String token_key
    ) throws Exception {
        Alumno user;
            user = (Alumno) sessionService.login(token_key);
            
        
            boolean b = alumnoService.responderPregunta(user.getId(), pregunta_id, respuesta);
            RespuestaPregunta r = new RespuestaPregunta();
            r.setCorrecto(b);
            preguntaService.aumentarCantRespuestas(pregunta_id,b);
            return new ResponseEntity<RespuestaPregunta>(r,HttpStatus.OK);
        
    }
    
    
    
}
