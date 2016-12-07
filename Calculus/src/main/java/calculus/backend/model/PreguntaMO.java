package calculus.backend.model;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="preguntaId")
public class PreguntaMO extends Pregunta{
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="opcion", joinColumns=@JoinColumn(name="pregunta_id"))
    @Column(name="opciones")
            List<String> opciones;
    
    @Column
    private String respuesta;
    
    public PreguntaMO() {
    }
    
    public PreguntaMO(Tema tema, String r) {
        super(tema);
        this.respuesta = r;
    }
    
    public List<String> getOpciones() {
        return opciones;
    }
    
    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }
    
    public String getRespuesta() {
        return respuesta;
    }
    
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    @Override
    public boolean esCorrectaRespuesta(String r) {
        return this.respuesta.equals(r);
    }
    
    @Override
    public Class getType() {
        return PreguntaMO.class;
    }
    
}