/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2.admin;

import calculus.backend.model.EstadisticasDashboard;
import calculus.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tavidian
 */
@RestController("v2AdminDashboardController")
@RequestMapping(value = "/v2/admin/dashboard")
public class DashboardController {
    
    @Autowired 
    DashboardService service;
    
    @RequestMapping(value = "/estadisticas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EstadisticasDashboard> getEstadisticas() {
        return new ResponseEntity<EstadisticasDashboard>(service.getEstadisticas(), HttpStatus.OK);
    }
    
}