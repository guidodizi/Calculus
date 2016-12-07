package calculus.backend.controller.v2.game;


import calculus.backend.model.Alumno;
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

@RestController("v2GameSessionController")
@RequestMapping(value = "/v2/game/session")
public class SessionController {
    
    @Autowired
    private SessionService service;
    
    /**
     * @param type
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = "/login/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(
            @PathVariable("type") String type,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        
        
            Alumno user = (Alumno) service.login(type, email, password);
            return new ResponseEntity<Alumno>(user, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/fblogin/alumno", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> fbLogin(
            @RequestParam(value="fbid") Long fbid,
            @RequestParam(value="name") String name) {
        
            Alumno user = (Alumno)service.fblogin(fbid, name);
            return new ResponseEntity<Alumno>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/registrar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrar(
            @RequestParam(value="email") String email,
            @RequestParam(value="name") String name,
            @RequestParam(value="password") String password){
        
            Alumno user = (Alumno)service.registrar(name, email, password);
            return new ResponseEntity<Alumno>(user, HttpStatus.OK);
    }    
}