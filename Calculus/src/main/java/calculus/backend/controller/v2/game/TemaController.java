/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2.game;

import calculus.backend.model.Alumno;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.Tema;
import calculus.backend.service.AlumnoService;
import calculus.backend.service.PreguntaService;
import calculus.backend.service.SessionService;
import calculus.backend.service.TemaService;
import java.util.Collection;
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

/**
 *
 * @author marti
 */
@RestController("v2GameTemaController")
@RequestMapping(value = TemaController.URI_PREFIX)
public class TemaController {
    
    public static final String URI_PREFIX = "/v2/game/temas";
    
    @Autowired
    PreguntaService preguntaService;
    @Autowired
    AlumnoService alumnoService;
    @Autowired
    SessionService sessionService;
    @Autowired
    TemaService temaService;
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllPreguntaAlumno(@RequestHeader(value="Authorization", required = false) String token_key) {
        Alumno user;
        user = (Alumno) sessionService.login(token_key);    
        return new ResponseEntity<Collection<Tema>>(temaService.findAll(),HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "/{id}/preguntas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPreguntasAlumno(
            @RequestHeader(value="Authorization", required = false) String token_key,
            @PathVariable("id") long id
    ) {
        Alumno user;
        try {
            
            user = (Alumno) sessionService.login(token_key);
            
        } catch (Exception ex) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Set<PreguntaAlumno>>(alumnoService.getPreguntasAlumnoTema(user.getId(),id),HttpStatus.OK);
    }
    
    
    
}
