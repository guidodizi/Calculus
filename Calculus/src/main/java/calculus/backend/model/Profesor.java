package calculus.backend.model;

import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@PrimaryKeyJoinColumn(name="persona_id")
@XmlRootElement
@DynamicUpdate(value=true)
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Profesor.findById", query = "SELECT p FROM Persona p WHERE p.id = :id"),
    @NamedQuery(name = "Profesor.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Profesor.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email"),
    @NamedQuery(name = "Profesor.findByPassword", query = "SELECT p FROM Persona p WHERE p.password = :password")})

public class Profesor extends Persona {
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="profesor")
    private Map<Long,Pregunta> preguntasCreadas;
    
    public Map<Long, Pregunta> getPreguntasCreadas() {
        return preguntasCreadas;
    }
    
    public void setPreguntasCreadas(Map<Long, Pregunta> preguntasCreadas) {
        this.preguntasCreadas = preguntasCreadas;
    }
    
}