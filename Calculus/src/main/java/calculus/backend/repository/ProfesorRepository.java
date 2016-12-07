/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author marti
 */
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    @Query("select p from Profesor p where p.email = :email")
    Profesor findByEmail(@Param("email") String email);
}
