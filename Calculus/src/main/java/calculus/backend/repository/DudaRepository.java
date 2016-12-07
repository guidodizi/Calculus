/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.Alumno;
import calculus.backend.model.Duda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author marti
 */
public interface DudaRepository extends JpaRepository<Duda, Long>{
    @Query("SELECT d FROM Duda d WHERE d.alumno = :alumno ORDER BY d.fecha")
    List<Duda> findDudasAlumno(@Param("alumno") Alumno alumno);
}
