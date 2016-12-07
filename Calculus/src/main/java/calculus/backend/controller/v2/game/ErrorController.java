/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2.game;

import calculus.backend.model.Error;
import calculus.backend.model.Alumno;
import calculus.backend.model.Pregunta;
import calculus.backend.service.ErrorService;
import calculus.backend.service.PreguntaService;
import calculus.backend.service.SessionService;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author mariano
 */

@RestController("v2GameErrorController")
@RequestMapping(value = "/v2/game/errores")
public class ErrorController {
    
    @Autowired
    ErrorService errorService;
    @Autowired
    SessionService sessionService;
    @Autowired
    PreguntaService preguntaService;
     
    private String getMapString() {
        return "/v2/game/errores";
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll( @RequestHeader(value="Authorization", required = false) String token_key ) {
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        
        List<Error> list = errorService.findErroresAlumno(user);
        if(list==null){
            return new ResponseEntity<List<Error>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Error>>(list,HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Error> findById(@RequestHeader(value="Authorization", required = false) String token_key, @PathVariable("id") long id) {
        
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
            Error obj = (Error) errorService.findById(id);
            if (obj == null) {
                return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
            } 
            if (!obj.getAlumno().equals(user)) {
                return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Error>(obj, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Error>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create( @RequestHeader(value="Authorization", required = false) String token_key, 
            @RequestParam(value="preguntaId") Long preguntaId,
            @RequestParam(value="descripcion") String descripcion,
            UriComponentsBuilder ucBuilder )  {
        
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
            } catch (Exception ex) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Pregunta p = preguntaService.findById(preguntaId);
            Error e = new Error(user,p,descripcion);
            Error er = errorService.create(e);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/errores/{id}").buildAndExpand(er.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (Exception ex2) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
            
            
        
    }
    
}
