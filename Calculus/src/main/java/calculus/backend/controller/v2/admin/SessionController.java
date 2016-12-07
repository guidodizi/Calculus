package calculus.backend.controller.v2.admin;


import calculus.backend.model.Profesor;
import calculus.backend.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("v2AdminSessionController")
@RequestMapping(value = "/v2/admin/session")
public class SessionController {
    
    @Autowired
            SessionService service;
    
    /**
     * @param type
     * @param email
     * @param password
     * @return si login incorrecto devuelve "no", si login correcto devuelve token
     */
    @RequestMapping(value = "/login/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(
            @PathVariable("type") String type,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        
        
            Profesor user = (Profesor) service.login(type, email, password);
            return new ResponseEntity<Profesor>(user, HttpStatus.OK);
    }
    
    
    
}