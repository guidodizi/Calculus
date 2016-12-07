package calculus.backend.service;



import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.Tema;
import calculus.backend.repository.PreguntaRepository;
import calculus.backend.repository.TemaRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PreguntaService")
@Transactional
public class PreguntaService extends AbstractService<Pregunta>{
    @Autowired
    PreguntaRepository repository;
    @Autowired
    TemaRepository temaRepository;
    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }

    @Override
    public Pregunta update(Pregunta p0) {
        //calcular y setear lista de preguntas que desbloquea
        Set<Pregunta> preguntasTodas = this.getPreguntasTema(p0.getTema().getId());
        Set<Long> idsPreguntasPosteriores = new HashSet();
        for (Pregunta p : preguntasTodas){
            if (p.getPreguntasPrevias().contains(p0.getId())){
                idsPreguntasPosteriores.add(p.getId());
            }
        }
        p0.setPreguntasQueDesbloquea(idsPreguntasPosteriores);
        
        //eliminar p0 de las preguntas posteriores de las preguntas que corresponde
        Pregunta p0Old = this.repository.findOne(p0.getId());
        List<Pregunta> preguntasPreviasOld = this.repository.findAll(p0Old.getPreguntasPrevias());
        for (Pregunta p : preguntasPreviasOld){
            p.getPreguntasQueDesbloquea().remove(p0.getId());
        }
        this.repository.save(preguntasPreviasOld);
        
        //agregar p0 a la lista de preguntas posteriores de las preguntas que corresponde
        List<Pregunta> preguntasPrevias = this.repository.findAll(p0.getPreguntasPrevias());
        for (Pregunta p : preguntasPrevias){
            p.getPreguntasQueDesbloquea().add(p0.getId());
        }
        this.repository.save(preguntasPrevias);
        
        return super.update(p0); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Pregunta create(Pregunta p1) {
        p1 = super.create(p1);
        List<Pregunta> preguntasPrevias = this.repository.findAll(p1.getPreguntasPrevias());
        for(Pregunta p : preguntasPrevias){
            p.getPreguntasQueDesbloquea().add(p1.getId());
        }
        this.repository.save(preguntasPrevias);
        
        return p1;
    }

    
    public Set<Pregunta> getPreguntasTema(long tema_id) {
            Tema t = temaRepository.findOne(tema_id);
            return this.repository.getPreguntasTema(t);
    }

    public void aumentarCantRespuestas(long pregunta_id, boolean esCorrecta) {
        
        Pregunta p = this.findById(pregunta_id);
        p.setCantRespuestas(p.getCantRespuestas()+1);
        if (esCorrecta) {
             p.setCantRespuestasCorrectas(p.getCantRespuestasCorrectas()+1);
        }
        this.update(p);
    }

    public Set<Pregunta> getPreguntas() {
        return repository.getPreguntas();
    }

    public Pregunta findByTitulo(String titulo, Tema tema) {
        return repository.findByTitulo(titulo, tema);
    }

    
}