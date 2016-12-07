package calculus.backend.service;



import calculus.backend.model.PreguntaCompletar;
import calculus.backend.repository.PreguntaCompletarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PreguntaCompletarService")
@Transactional
public class PreguntaCompletarService extends AbstractService<PreguntaCompletar>{
    
    @Autowired
    PreguntaCompletarRepository repository;
    
    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }
    
    
    
    
}