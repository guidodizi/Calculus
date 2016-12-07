package calculus.backend.service;



import calculus.backend.repository.DudaRepository;
import calculus.backend.model.Alumno;
import calculus.backend.model.Duda;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DudaService")
@Transactional
public class DudaService extends AbstractService<Duda> {

    @Autowired
    DudaRepository repository;
    
    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }
    
    public List<Duda> findDudasAlumno(Alumno alumno) {
        return this.repository.findDudasAlumno(alumno);
    }
    
}