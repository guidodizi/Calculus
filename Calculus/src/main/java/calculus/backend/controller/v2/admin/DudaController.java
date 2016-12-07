package calculus.backend.controller.v2.admin;

import calculus.backend.model.Duda;
import calculus.backend.model.Profesor;
import calculus.backend.service.DudaService;
import calculus.backend.service.SessionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("v2AdminDudaController")
@RequestMapping(value = "/v2/admin/dudas")
public class DudaController {
    
    @Autowired
    DudaService service;
    @Autowired
    SessionService sessionService;
    
    protected DudaService getService() {
        return this.service;
    }
    
    protected String getMapString() {
        return "/dudas";
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll( @RequestHeader(value="Authorization", required = false) String token_key ) {
        try {
            Profesor prof = (Profesor) sessionService.login(token_key);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
        
        List<Duda> list = getService().findAll();
        if(list==null){
            return new ResponseEntity<List<Duda>>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<Duda>>(list,HttpStatus.OK);
        }
        
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Duda> findById(@RequestHeader(value="Authorization", required = false) String token_key, @PathVariable("id") long id) {
            try {
                Profesor prof = (Profesor) sessionService.login(token_key);
            } catch (Exception e) {
                return new ResponseEntity<Duda>(HttpStatus.UNAUTHORIZED);
            }
            
            Duda obj = (Duda) getService().findById(id);
            if (obj == null) {
                return new ResponseEntity<Duda>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Duda>(obj, HttpStatus.OK);   
    }
    
    
    
    @RequestMapping(value = "/{id}/respuesta", method = RequestMethod.PUT)
    public ResponseEntity<Void> setRespuesta(@RequestHeader(value="Authorization", required = false) String token_key, 
            @PathVariable("id") Long id,
            @RequestParam(value="r") String respuesta, 
            UriComponentsBuilder ucBuilder) {
        
        try {
            Profesor prof = (Profesor) sessionService.login(token_key);
        } catch (Exception e) {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        Duda d = getService().findById(id);
        if (d == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        if (d.getRespuesta() != null) {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
        d.setRespuesta(respuesta);
        getService().update(d);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(d.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
        
        
    }
    
    @RequestMapping(value = "/{id}/leida", method = RequestMethod.PUT)
    public ResponseEntity<Void> setLeida(@RequestHeader(value="Authorization", required = false) String token_key, 
            @PathVariable("id") Long id,
            @RequestParam(value="l") Boolean leida, 
            UriComponentsBuilder ucBuilder) {
        
        try {
            Profesor prof = (Profesor) sessionService.login(token_key);

            Duda d = getService().findById(id);
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
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Void> replace(@RequestHeader(value="Authorization", required = false) String token_key, @RequestBody Duda obj, UriComponentsBuilder ucBuilder) {
        try {

            Profesor prof = (Profesor) sessionService.login(token_key);

        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
        
        Duda d = getService().findById(obj.getId());
        if (d == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        obj.setAlumno(d.getAlumno());
        getService().update(obj);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(obj.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
}
    
    
    
}
