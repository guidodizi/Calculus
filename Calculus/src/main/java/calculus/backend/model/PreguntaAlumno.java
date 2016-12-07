package calculus.backend.model;

import calculus.backend.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PreguntaAlumno implements Serializable {
    
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="pregunta_id")
    private Pregunta pregunta;
    
    /**
     * True si respondio bien la pregunta.
     */
    @JsonView(View.All.class)
    @Column
    private boolean respuestaCorrecta = false;
    
    /**
     * Cantidad de veces que el alumno asociado respondio mal la pregunta.
     */
    @JsonView(View.All.class)
    @Column
    private int respuestaIncorrecta = 0;
    
    @JsonView(View.All.class)
    @Column
    private boolean blocked;
    
    public PreguntaAlumno() {
    }
    
    public PreguntaAlumno(Pregunta pregunta, boolean blocked) {
        this(pregunta);
        this.blocked = blocked;
    }
    
    public PreguntaAlumno(Pregunta pregunta){
        this.pregunta = pregunta;
    }
    
    public Long getPreguntaId(){
        return this.pregunta.getId();
    }
    
    public boolean fueRespondida(){
        return (this.respuestaCorrecta) | (this.respuestaIncorrecta>0);
    }
    
    public Pregunta getPregunta() {
        return pregunta;
    }
    
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
    
    public boolean isRespuestaCorrecta() {
        return respuestaCorrecta;
    }
    
    public void setRespuestaCorrecta(boolean respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
    
    /**
     * 
     * @return Cantidad de veces que el alumno asociado respondio mal la pregunta.
     */
    public int getRespuestaIncorrecta() {
        return respuestaIncorrecta;
    }
    
    public void setRespuestaIncorrecta(int respuestaIncorrecta) {
        this.respuestaIncorrecta = respuestaIncorrecta;
    }
    
    public boolean isBlocked() {
        return blocked;
    }
    
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    
}