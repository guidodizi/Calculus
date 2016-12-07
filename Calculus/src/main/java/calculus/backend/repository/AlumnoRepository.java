/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author marti
 */
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    @Query("select a from Alumno a where a.email = :email")
    Alumno findByEmail(@Param("email") String email);

    @Query("SELECT a FROM Alumno a ORDER BY a.puntaje DESC")
    public List<Alumno> getRanking();
    
}
