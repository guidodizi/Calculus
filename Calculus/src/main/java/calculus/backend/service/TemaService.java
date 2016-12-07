package calculus.backend.service;


import calculus.backend.model.Tema;
import calculus.backend.repository.TemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service("TemaService")
public class TemaService extends AbstractService<Tema>{
    @Autowired
    TemaRepository repository;
    
    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }

    public Tema findByTitulo(String titulo) {
        return repository.findByTitulo(titulo);
    }
}
