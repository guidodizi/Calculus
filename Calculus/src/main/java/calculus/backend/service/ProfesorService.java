package calculus.backend.service;



import calculus.backend.model.Profesor;
import calculus.backend.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ProfesorService")
@Transactional
public class ProfesorService extends AbstractService<Profesor> {
    @Autowired
    ProfesorRepository repository;
    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }
    
}
