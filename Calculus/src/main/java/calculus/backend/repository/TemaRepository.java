/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author marti
 */
public interface TemaRepository extends JpaRepository<Tema, Long>{

    @Query("select t from Tema t where t.tema = :titulo")
    public Tema findByTitulo(@Param("titulo") String titulo);
    
}
