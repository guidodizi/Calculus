/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.Pregunta;
import calculus.backend.model.Tema;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author marti
 */
public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    @Query("SELECT p FROM Pregunta p WHERE p.tema = :tema")
    public Set<Pregunta> getPreguntasTema(@Param("tema") Tema t);

    //@Query("SELECT p FROM Pregunta p WHERE p.id IN (SELECT responder_preg_id FROM PRECEDENCIA_PREGUNTAS WHERE desbloquea_preg_id = :id)")
    @Query("SELECT p FROM Pregunta p")
    public Set<Pregunta> getPreguntas();
    
    //@Query(value="SELECT IFNULL(SUM(cantrespuestas),0) FROM Pregunta p",nativeQuery = true)
    @Query(value="SELECT COALESCE(SUM(cantrespuestas),0) FROM Pregunta p",nativeQuery = true)
    public long getCantRespuestas();
    
    //@Query(value="SELECT IFNULL(SUM(cantrespuestascorrectas),0) FROM Pregunta p",nativeQuery = true)
    @Query(value="SELECT COALESCE(SUM(cantrespuestascorrectas),0) FROM Pregunta p",nativeQuery = true)
    public long getCantRespuestasCorrectas();
    
    @Query("SELECT p FROM Pregunta p WHERE cantrespuestas > 0 AND (CAST(cantrespuestascorrectas AS float)/CAST (cantrespuestas AS float)*100) < 10")
    public Set<Pregunta> getPreguntasDificiles();
    
    @Query("select p from Pregunta p where p.titulo = :titulo and p.tema = :tema")
    public Pregunta findByTitulo(@Param("titulo") String titulo, @Param("tema") Tema tema);
}
