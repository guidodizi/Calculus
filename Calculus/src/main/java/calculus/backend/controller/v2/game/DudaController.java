/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2.game;

import calculus.backend.model.Alumno;
import calculus.backend.model.Duda;
import calculus.backend.model.Profesor;
import calculus.backend.service.DudaService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author marti
 */
@RestController("v2GameDudaController")
@RequestMapping(value = "/v2/game/dudas")
public class DudaController {
    @Autowired
    DudaService dudaService;
    @Autowired
    SessionService sessionService;
    
     private String getMapString() {
        return "/dudas";
    }
        
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll( @RequestHeader(value="Authorization", required = false) String token_key ) {
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
        } catch (Exception ex) {
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        
        List<Duda> list = dudaService.findDudasAlumno(user);
        if(list==null){
            return new ResponseEntity<List<Duda>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Duda>>(list,HttpStatus.OK);
        }
        
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Duda> findById(@RequestHeader(value="Authorization", required = false) String token_key, @PathVariable("id") long id) {
        
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
            //checkear que la duda sea del alumno
            Duda obj = (Duda) dudaService.findById(id);
            if (obj == null) {
                return new ResponseEntity<Duda>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Duda>(obj, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<Duda>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> create( @RequestHeader(value="Authorization", required = false) String token_key, 
            @RequestBody Duda obj, 
            UriComponentsBuilder ucBuilder )  {
        
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);

            obj.setAlumno(user);
            Date f = new Date(Calendar.getInstance().getTimeInMillis());
            obj.setFecha(f);
            obj.setLeida(false);
            Duda d = dudaService.create(obj);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/dudas/{id}").buildAndExpand(obj.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
            
        } catch (Exception ex) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Void> replace(@RequestHeader(value="Authorization", required = false) String token_key, @RequestBody Duda obj, UriComponentsBuilder ucBuilder) {
        
        Alumno user;
        try {
            user = (Alumno) sessionService.login(token_key);
            
        } 
        catch(Exception ex){
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        
        if (!dudaService.existsId(obj.getId())) {
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        obj.setAlumno(user);
        dudaService.update(obj);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(obj.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
        
    }
    
}
