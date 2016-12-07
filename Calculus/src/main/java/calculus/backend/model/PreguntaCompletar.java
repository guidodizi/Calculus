package calculus.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Tipo de pregunta que se le plantea al alumno, donde hay que rellenar un campo como respuesta.
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name="preguntaId")
public class PreguntaCompletar extends Pregunta{
    
    public PreguntaCompletar() {
    }
    
    public PreguntaCompletar(Tema tema) {
        super(tema);
    }
    
    public PreguntaCompletar(Tema tema, String respuesta) {
        super(tema);
        this.respuesta = respuesta;
    }
    
    @Override
    public boolean esCorrectaRespuesta(String r) {
        return this.respuesta.equals(r);
    }
    
    @JsonIgnore
    @Override
    public Class getType() {
        return PreguntaCompletar.class;
    }
    
}