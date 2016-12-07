package calculus.backend.service;


import calculus.backend.model.Error;
import calculus.backend.model.Alumno;
import calculus.backend.model.Pregunta;
import calculus.backend.repository.ErrorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ErrorService")
@Transactional
public class ErrorService extends AbstractService<Error> {

    @Autowired
    ErrorRepository repository;
    
    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }
    
    public List<Error> findErroresAlumno(Alumno user) {
        return this.repository.findErroresAlumno(user);
    }
    
    public List<Error> findErrorByPregunta(Pregunta pregunta) {
        return this.repository.findErrorByPregunta(pregunta);
    }
    
}