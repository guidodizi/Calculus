/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.Alumno;
import java.util.List;
import calculus.backend.model.Error;
import calculus.backend.model.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author mariano
 */
public interface ErrorRepository extends JpaRepository<Error, Long> {
   
    @Query("SELECT e FROM Error e WHERE e.alumno = :alumno ORDER BY e.fecha")
    public List<Error> findErroresAlumno(@Param("alumno") Alumno alumno);

    @Query("SELECT e FROM Error e WHERE e.pregunta = :pregunta ORDER BY e.fecha")
    public List<Error> findErrorByPregunta(@Param("pregunta") Pregunta pregunta);

}