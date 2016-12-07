package calculus.backend.model;

import calculus.backend.View;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PreguntaCompletar.class, name = "completar"),
    @JsonSubTypes.Type(value = PreguntaMO.class, name = "mo")
})
@Entity
@Table(name = "Pregunta")
@Inheritance(strategy=InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findById", query = "SELECT p FROM Pregunta p WHERE p.id = :id"),
    @NamedQuery(name = "Pregunta.findByTema", query = "SELECT p FROM Pregunta p WHERE p.tema = :tema")
})
public abstract class Pregunta extends AbstractModel{
    
    private static final long serialVersionUID = 1L;
    @JsonView(View.All.class)
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    
    @JsonView(View.AdminSummary.class)
    @Column
            String respuesta;
    
    @JsonView(View.AdminSummary.class)
    @Column
    private int puntos;
    
    //Para saber si se desbloquea automaticamente al inicio del juego
    @JsonView(View.AdminSummary.class)
    @Column
    private boolean inicial = false;
    
    @JsonView(View.All.class)
    @Column
    private String titulo;

    @JsonView(View.All.class)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="profesor_id")
    private Profesor profesor;
    
    /*
    El atributo "descripcion" se guarda en la tabla "Pregunta"
    atributo -> columna
    descripcion.texto -> descripcion_texto
    descripcion.imagen -> descripcion_imagen
    */
    @JsonView(View.UserExtended.class)
    @AttributeOverrides({
        @AttributeOverride(name="texto",column=@Column(name="descripcion_texto")),
        @AttributeOverride(name="imagen",column=@Column(name="descripcion_imagen")),
        @AttributeOverride(name="video",column=@Column(name="descripcion_video"))
    })
    @Embedded
    private Informacion descripcion;
    
    /*
    El atributo "tip" se guarda en la tabla "Pregunta"
    atributo -> columna
    tip.texto -> tip_texto
    descripcion.imagen -> tip_imagen
    */
    @JsonView(View.UserExtended.class)
    @AttributeOverrides({
        @AttributeOverride(name="texto",column=@Column(name="tip_texto")),
        @AttributeOverride(name="imagen",column=@Column(name="tip_imagen")),
        @AttributeOverride(name="video",column=@Column(name="tip_video"))
    })
    @Embedded
    private Informacion tip;
    
    @JsonView(View.UserExtended.class)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="TEMA_ID")
    private Tema tema;
       
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="preguntasQueDebloquea", joinColumns=@JoinColumn(name="pregunta_id"))
    @Column(name="desbloquea")
    private Set<Long> preguntasQueDesbloquea = new HashSet();
    
    
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="preguntasPrevias", joinColumns=@JoinColumn(name="pregunta_id"))
    @Column(name="previa")
    private Set<Long> preguntasPrevias = new HashSet();
    
    
    private int cantRespuestas;
    
    private int cantRespuestasCorrectas;
    
    
    //Constructors
    //------------------------------------
    public Pregunta() {
        this.cantRespuestas = 0;
        this.cantRespuestasCorrectas = 0;
    }
    
    public Pregunta( Tema tema) {
        this.tema = tema;
    }
    
    public Pregunta (Tema tema, boolean esInicial ){
        this(tema);
        this.inicial = esInicial;
    }
    
    
    //Funciones
    //------------------------------------
    public abstract boolean esCorrectaRespuesta(String r);
    
    public abstract Class getType();
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Informacion getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Informacion descripcion) {
        this.descripcion = descripcion;
    }

    public Informacion getTip() {
        return tip;
    }

    public void setTip(Informacion tip) {
        this.tip = tip;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public Set<Long> getPreguntasQueDesbloquea() {
        return preguntasQueDesbloquea;
    }

    public void setPreguntasQueDesbloquea(Set<Long> preguntasQueDesbloquea) {
        this.preguntasQueDesbloquea = preguntasQueDesbloquea;
    }

    public Set<Long> getPreguntasPrevias() {
        return preguntasPrevias;
    }

    public void setPreguntasPrevias(Set<Long> preguntasPrevias) {
        this.preguntasPrevias = preguntasPrevias;
    }

    public int getCantRespuestas() {
        return cantRespuestas;
    }

    //GETTER & SETTERS
    //------------------------------------
    public void setCantRespuestas(int cantRespuestas) {   
        this.cantRespuestas = cantRespuestas;
    }

    public void setCantRespuestasCorrectas(int cantRespuestasCorrectas) {
        this.cantRespuestasCorrectas = cantRespuestasCorrectas;
    }
    
    public float getProcentajeRespuestasCorrectas() {
        return (float) ((float)this.cantRespuestasCorrectas / this.cantRespuestas * 100);
    }

    public int getCantRespuestasCorrectas() {
        return cantRespuestasCorrectas;
    }
}