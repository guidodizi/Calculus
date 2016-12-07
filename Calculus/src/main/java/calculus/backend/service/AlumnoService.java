package calculus.backend.service;


import calculus.backend.model.Alumno;
import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.repository.AlumnoRepository;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AlumnoService")
@Transactional
public class AlumnoService extends AbstractService<Alumno> {
    
    @Autowired
    AlumnoRepository repository;
    
    @Autowired
    PreguntaService preguntaService;

    @Override
    protected JpaRepository getRepository() {
        return this.repository;
    }
    
    
    
    public Alumno findByEmail(String email) {
        return this.repository.findByEmail(email);
    }
    
    /**
     * Asigna todas las preguntas disponibles al alumno. Parece m�s un m�todo de testeo.
     * @param alumno_id
     */
    public void asignarPreguntasAlAlumno(Long alumno_id){
        PreguntaAlumno preguntaAlumno = null;
        Alumno alumno = this.findById(alumno_id);
        
        if (alumno.getPreguntasAlumno() == null) alumno.setPreguntasAlumno(new HashMap());
        Map<Long,PreguntaAlumno> preguntasAlumno = alumno.getPreguntasAlumno();
        
        List<Pregunta> todasPreguntas = preguntaService.findAll();
        for (Pregunta p : todasPreguntas) {
            
            Set<Long> previasPreg = p.getPreguntasPrevias();
            boolean blocked = !p.isInicial();
            for (Long pId : previasPreg) {
                if (preguntasAlumno.containsKey(pId) && blocked) {
                    blocked = blocked && !preguntasAlumno.get(pId).isRespuestaCorrecta();
                }
            }
            
            if (!preguntasAlumno.containsKey(p.getId())){
                preguntaAlumno = new PreguntaAlumno(p, blocked);
                preguntasAlumno.put(p.getId(), preguntaAlumno);
            } else {
                blocked = blocked && !preguntasAlumno.get(p.getId()).isRespuestaCorrecta();
                preguntasAlumno.get(p.getId()).setBlocked(blocked);
            }
        }
        this.update(alumno);
    }
    
    public Set<PreguntaAlumno> getPreguntasAlumno(Long alumno_id){
        this.asignarPreguntasAlAlumno(alumno_id);
        Alumno alumno = this.findById(alumno_id);
        return new HashSet(alumno.getPreguntasAlumno().values());
    }
    
    public PreguntaAlumno getPreguntaAlumno(Long alumno_id,Long pregunta_id){
        this.asignarPreguntasAlAlumno(alumno_id);
        Alumno alumno = this.findById(alumno_id);
        return (alumno.getPreguntasAlumno()).get(pregunta_id);
    }
    
    public Set<PreguntaAlumno> getPreguntasAlumnoTema(Long alumno_id, Long tema_id){
        this.asignarPreguntasAlAlumno(alumno_id);
        Alumno alumno = this.findById(alumno_id);
        Set<PreguntaAlumno> resultList = new HashSet();
        
        for (PreguntaAlumno p : alumno.getPreguntasAlumno().values()){
            if (p.getPregunta().getTema().getId().longValue() == tema_id.longValue()){
                resultList.add(p);
            }
        }
        return resultList;
    }
    
    public boolean responderPregunta(Long alumno_id, Long pregunta_id, String respuesta) throws Exception{
        Alumno alumno = this.findById(alumno_id);
        PreguntaAlumno pA = alumno.getPreguntasAlumno().get(pregunta_id);
        
        if (pA == null)
            throw new Exception("El alumno no tiene dicha pregunta.");
        if (pA.isRespuestaCorrecta())
            throw new Exception("El alumno ya respondio correctamente esta pregunta.");
        if (pA.isBlocked()){
            throw new Exception("La pregunta esta bloqueada para el alumno.");
        }
        Pregunta p = pA.getPregunta();
        Boolean correcta = p.esCorrectaRespuesta(respuesta);
        if (correcta){
            alumno.setPuntaje(alumno.getPuntaje() + p.getPuntos());
            pA.setRespuestaCorrecta(true);
            Collection<Pregunta> preguntasDesbloqueadas = preguntaService.findAll(p.getPreguntasQueDesbloquea());
            for (Pregunta preguntaDesbloquear : preguntasDesbloqueadas){
                Map<Long,PreguntaAlumno> preguntasAlumno = alumno.getPreguntasAlumno();
                PreguntaAlumno preguntaAlumnoDesbloquear = preguntasAlumno.get(preguntaDesbloquear.getId());
                if (preguntaAlumnoDesbloquear != null){
                    preguntaAlumnoDesbloquear.setBlocked(false);
                }
                else{
                    //Lo mejor seria que nunca se llegue a esta parte
                    preguntaAlumnoDesbloquear = new PreguntaAlumno(preguntaDesbloquear,false);
                    alumno.getPreguntasAlumno().put(preguntaDesbloquear.getId(), preguntaAlumnoDesbloquear);
                }
            }
        }
        else{
            pA.setRespuestaCorrecta(false);
            pA.setRespuestaIncorrecta(pA.getRespuestaIncorrecta()+1);
        }
        this.update(alumno);
        return correcta;
    }

    /**
     * Devuelve el alumno con id pasado, o EntityNotFoundException si no lo encontro.
     * @param id
     * @return 
     */
    @Override
    @Transactional
    public Alumno findById(long id)
    {
        Alumno entity = (Alumno) this.getRepository().findOne(id);
        
        if (entity == null) {
            throw new EntityNotFoundException("No se encuentra una entidad 'Alumno' con id='" + id + "'");
        }
        calcularPreguntasCorrectasEIncorrectas(entity);
        
        return entity;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    @Transactional
    public List<Alumno> findAll() {
        List<Alumno> alumnoList = this.getRepository().findAll();
        for (Alumno alumno : alumnoList)
        {
            calcularPreguntasCorrectasEIncorrectas(alumno);
        }
        return alumnoList;
    }
    

    /**
     * Dado un alumno, calcula la cantidad de preguntas correctamente e incorrectamente constadas por el.
     * @param entity 
     */
    protected void calcularPreguntasCorrectasEIncorrectas(Alumno entity)
    {
        int cantRespIncorr = 0;
        int cantRespCorr = 0;
        for (PreguntaAlumno preguntaAlumno : (Collection<PreguntaAlumno>) entity.getPreguntasAlumno().values())
        {
            cantRespIncorr = cantRespIncorr + preguntaAlumno.getRespuestaIncorrecta();
            if (preguntaAlumno.isRespuestaCorrecta())
            {
                cantRespCorr++;
            }
        }
        
        entity.setCantidadPreguntasIncorrectas(cantRespIncorr);
        entity.setCantidadPreguntasCorrectas(cantRespCorr);
    }

    public List<Alumno> getRanking() {
        return repository.getRanking();
    }
    
}
