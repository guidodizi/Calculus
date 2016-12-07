package calculus.backend.controller.v2.admin;


import calculus.backend.model.Error;
import calculus.backend.model.Profesor;
import calculus.backend.service.ErrorService;
import calculus.backend.service.SessionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mariano
 */
@RestController("v2AdminErrorController")
@RequestMapping(value = "/v2/admin/errores")
public class ErrorController {
    
    @Autowired
    ErrorService service;
    @Autowired
    SessionService sessionService;
    
    protected ErrorService getService() {
        return this.service;
    }
    
    protected String getMapString() {
        return "/v2/admin/errores";
    }
    
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll( @RequestHeader(value="Authorization", required = false) String token_key ) {
        try {
            Profesor prof = (Profesor) sessionService.login(token_key);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        
        List<Error> list = getService().findAll();
        if(list==null){
            return new ResponseEntity<List<Error>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Error>>(list,HttpStatus.OK);
        }
    }
    
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Error> findById(@RequestHeader(value="Authorization", required = false) String token_key, @PathVariable("id") long id) {
            try {
                Profesor prof = (Profesor) sessionService.login(token_key);
            } catch (Exception e) {
                return new ResponseEntity<Error>(HttpStatus.UNAUTHORIZED);
            }
            
            Error obj = (Error) getService().findById(id);
            if (obj == null) {
                return new ResponseEntity<Error>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Error>(obj, HttpStatus.OK);   
    }
    
    
    @RequestMapping(value = "/{id}/leida", method = RequestMethod.PUT)
    public ResponseEntity<Void> setLeida(@RequestHeader(value="Authorization", required = false) String token_key, 
            @PathVariable("id") Long id,
            @RequestParam(value="leida") Boolean leida, 
            UriComponentsBuilder ucBuilder) {
        
        try {
            Profesor prof = (Profesor) sessionService.login(token_key);

            Error d = getService().findById(id);
            if (d == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            d.setLeida(true);
            getService().update(d);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(d.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
    }
}