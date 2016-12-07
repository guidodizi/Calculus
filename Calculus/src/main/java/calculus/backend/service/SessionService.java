package calculus.backend.service;

import calculus.backend.repository.AlumnoRepository;
import calculus.backend.model.Persona;
import calculus.backend.model.Token;
import calculus.backend.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import calculus.backend.exception.BadCredentialsException;
import calculus.backend.model.Alumno;
import org.springframework.util.Assert;

@Service("SessionService")
@Transactional
public class SessionService
{
    @Autowired
    ProfesorRepository profesorDAO;
    @Autowired
    AlumnoRepository alumnoRepository;

    public Persona login(String type, String email, String password) throws RuntimeException
    {
        Token t = new Token(type, email, password);
        return this.login(t);
    }

    public Persona login(String key) throws RuntimeException
    {
        if (key == null || key.length() == 0) {
            throw new BadCredentialsException("'Authorization token' no puede ser vacío ni nulo.");
        }
        Token token = new Token(key);
        return this.login(token);
    }

    public Persona login(Token t) throws RuntimeException
    {
        Persona p;
        if (t.getUserType().equals(Token.TOKEN_TYPE_ALUMNO))
        {
            p = alumnoRepository.findByEmail(t.getUserEmail());
        }
        else
        {
            p = profesorDAO.findByEmail(t.getUserEmail());
        }

        if (p == null)
        {
            throw new BadCredentialsException("'" + t.getUserEmail() + "' does not exist");
        }
        if (!p.getPassword().equals(t.getUserPassword()))
        {
            throw new BadCredentialsException("Wrong password");
        }
        
        p.setToken_key(t.getKey());
        return p;

    }
    
    public Persona fblogin(Long fbid, String nombre) {
        String email = fbid.toString() + "@facebook.com";
        String type = "alumno";
        String password = "FB_" + fbid.toString();
        Token t = new Token(type, email, password);
        
        Persona p = alumnoRepository.findByEmail(t.getUserEmail());
        if (p == null) {
            Alumno a = new Alumno();
            a.setNombre(nombre);
            a.setPassword(password);
            a.setEmail(email);
            alumnoRepository.save(a);
            return this.fblogin(fbid, nombre);
        }
        
        p.setToken_key(t.getKey());
        return p;
        
    }
    
    public Persona registrar(String nombre, String email, String password ) {
        String type = "alumno";
        Token t = new Token(type, email, password);
        
        Persona p = alumnoRepository.findByEmail(t.getUserEmail());
        if (p != null) {
            throw new BadCredentialsException("'" + t.getUserEmail() + "' already exist");
        }
        
        Alumno a = new Alumno();
        a.setNombre(nombre);
        a.setPassword(password);
        a.setEmail(email);
        alumnoRepository.save(a);
        a.setToken_key(t.getKey());
        return a;
        
    }

}
