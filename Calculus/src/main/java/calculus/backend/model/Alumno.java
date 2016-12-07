package calculus.backend.model;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@PrimaryKeyJoinColumn(name="persona_id")
@XmlRootElement
@DynamicUpdate(value=true)
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Alumno.findById", query = "SELECT p FROM Persona p WHERE p.id = :id"),
    @NamedQuery(name = "Alumno.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Alumno.findByEmail", query = "SELECT p FROM Persona p WHERE p.email = :email"),
    @NamedQuery(name = "Alumno.findByPassword", query = "SELECT p FROM Persona p WHERE p.password = :password")})

public class Alumno extends Persona {
    
    @Basic(optional = false)
    @Column(name = "puntaje")
    private int puntaje;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
            name="preguntaAlumno",
            joinColumns=@JoinColumn(name="alumno_id")
    )

    private Map<Long,PreguntaAlumno> preguntasAlumno = new HashMap();

    /**
     * El total de preguntas incorrectamente respondidas por el alumno.
     */
    @Transient
    private Integer cantidadPreguntasIncorrectas;
    
    /**
     * El total de preguntas correctamente respondidas por el alumno.
     */
    @Transient
    private Integer cantidadPreguntasCorrectas;
    
    public int getPuntaje() {
        return puntaje;
    }
    
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    public Map<Long, PreguntaAlumno> getPreguntasAlumno() {
        return preguntasAlumno;
    }
    
    public void setPreguntasAlumno(Map<Long, PreguntaAlumno> preguntasAlumno) {
        this.preguntasAlumno = preguntasAlumno;
    }
    
    //Dudas
//    @ElementCollection(fetch=FetchType.EAGER)
//    @CollectionTable(
//          name="dudasAlumno",
//          joinColumns=@JoinColumn(name="alumno_id")
//    )
//    private Set<Duda> dudasAlumno;
//
//    public Set<Duda> getDudasAlumno() {
//        return dudasAlumno;
//    }
//

    /**
     * @return the cantidadPreguntasIncorrectas
     */
    public Integer getCantidadPreguntasIncorrectas()
    {
        return cantidadPreguntasIncorrectas;
    }

    /**
     * @param cantidadPreguntasIncorrectas the cantidadPreguntasIncorrectas to set
     */
    public void setCantidadPreguntasIncorrectas(Integer cantidadPreguntasIncorrectas)
    {
        this.cantidadPreguntasIncorrectas = cantidadPreguntasIncorrectas;
    }

    /**
     * @return the cantidadPreguntasCorrectas
     */
    public Integer getCantidadPreguntasCorrectas()
    {
        return cantidadPreguntasCorrectas;
    }

    /**
     * @param cantidadPreguntasCorrectas the cantidadPreguntasCorrectas to set
     */
    public void setCantidadPreguntasCorrectas(Integer cantidadPreguntasCorrectas)
    {
        this.cantidadPreguntasCorrectas = cantidadPreguntasCorrectas;
    }
    
}