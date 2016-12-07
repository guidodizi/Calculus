/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.repository;

import calculus.backend.model.PreguntaCompletar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author marti
 */
public interface PreguntaCompletarRepository extends JpaRepository<PreguntaCompletar, Long>{
    
}
