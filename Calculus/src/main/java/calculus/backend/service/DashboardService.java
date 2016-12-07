package calculus.backend.service;



import calculus.backend.model.EstadisticasDashboard;
import calculus.backend.repository.AlumnoRepository;
import calculus.backend.repository.DudaRepository;
import calculus.backend.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("DashboardService")
@Transactional
public class DashboardService {
    @Autowired
    PreguntaRepository preguntaDAO;
    @Autowired
    AlumnoRepository alumnoDAO;
    @Autowired
    DudaRepository dudaDAO;
    
    
    public EstadisticasDashboard getEstadisticas() {
        
        EstadisticasDashboard e = new EstadisticasDashboard(
                preguntaDAO.getCantRespuestasCorrectas(),
                preguntaDAO.getCantRespuestas(),
                preguntaDAO.getPreguntasDificiles(),
                dudaDAO.count(),
                alumnoDAO.count()
        );
        return e;
    }
    
    
    
}