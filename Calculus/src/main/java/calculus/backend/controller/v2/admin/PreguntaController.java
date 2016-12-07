package calculus.backend.controller.v2.admin;

import calculus.backend.model.Error;
import static calculus.backend.controller.v2.admin.AbstractController.AUTHORIZATION;
import calculus.backend.exception.BadCredentialsException;
import calculus.backend.exception.EntityHasAssociationException;
import calculus.backend.model.Alumno;
import calculus.backend.model.Pregunta;
import calculus.backend.model.PreguntaAlumno;
import calculus.backend.model.PreguntaCompletar;
import calculus.backend.model.Profesor;
import calculus.backend.service.AbstractService;
import calculus.backend.service.AlumnoService;
import calculus.backend.service.ErrorService;
import calculus.backend.service.PreguntaService;
import calculus.backend.service.ProfesorService;
import calculus.backend.service.SessionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("v2AdminPreguntaController")
@RequestMapping(value = PreguntaController.URI_PREFIX)
public class PreguntaController extends AbstractController<Pregunta>{
    
    public static final String URI_PREFIX = "/v2/admin/preguntas";
    
    @Autowired
    private PreguntaService preguntaService;
    
    @Autowired
    private AlumnoService alumnoService;
    
    @Autowired
    private ProfesorService profesorService;
    
    @Autowired
    private ErrorService errorService;
    
    @Autowired
    private SessionService sessionService;
    
    @Override
    protected AbstractService getService() {
        return this.preguntaService;
    }
    
    @Override
    protected String getMapString() {
        return URI_PREFIX;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Override
    public ResponseEntity<?> create(@RequestBody Pregunta obj, UriComponentsBuilder ucBuilder,
                                    @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken) {
        
        try {
            Profesor p = (Profesor) sessionService.login(authorizationToken);      
        } catch (ClassCastException e) {
            throw new BadCredentialsException("La persona pasada no es un profesor"); // TODO: esto deberia estar en el login
        }
        
        Collection<Long> ids;
        Map<Long,Pregunta> preguntasQueDesbloquea;
        PreguntaCompletar pc;
        
        pc = (PreguntaCompletar) obj;
        
        if (camposCorrectos(obj)) {
            
            Pregunta p = preguntaService.findByTitulo(obj.getTitulo(), obj.getTema());
            
            if (p != null) {
                return new ResponseEntity<String>("Ya existe una pregunta con ese titulo en ese tema.",HttpStatus.FORBIDDEN);
            } else {
                preguntaService.create(pc);

                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(pc.getId()).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.CREATED); 
            }
            
            
        } else {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

    }
    
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Override
    public ResponseEntity<Void> update(@RequestBody Pregunta obj, UriComponentsBuilder ucBuilder,
                                    @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken) {
        
        try {
            Profesor p = (Profesor) sessionService.login(authorizationToken);      
        } catch (ClassCastException e) {
            throw new BadCredentialsException("La persona pasada no es un profesor"); // TODO: esto deberia estar en el login
        }
        
        Collection<Long> ids;
        Map<Long,Pregunta> preguntasQueDesbloquea;
        PreguntaCompletar pc;
        
        pc = (PreguntaCompletar) obj;
        
        if (camposCorrectos(obj)) {
            
            Pregunta p = preguntaService.findByTitulo(obj.getTitulo(), obj.getTema());
            
            if (p != null && !p.getId().equals(pc.getId())) {
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
            } else {
                
               Pregunta Old_p = preguntaService.findById(pc.getId());
               int ptsDiff = pc.getPuntos() - Old_p.getPuntos();
               
               pc.setCantRespuestas(Old_p.getCantRespuestas());
               pc.setCantRespuestasCorrectas(Old_p.getCantRespuestasCorrectas());
               preguntaService.update(pc);
               
               //update las PreguntaAlumno (solo si cambia el puntaje)
                
                if (ptsDiff != 0) {
                    for (Alumno alumno : alumnoService.findAll())
                    {
                        PreguntaAlumno preguntaAlumnoFound = null;
                        for (PreguntaAlumno preguntaAlumno : new ArrayList<PreguntaAlumno>(alumno.getPreguntasAlumno().values()))
                        {
                            if (preguntaAlumno.getPreguntaId().equals(pc.getId()))
                            {
                                preguntaAlumnoFound = preguntaAlumno;
                                if (preguntaAlumnoFound.isRespuestaCorrecta())
                                {
                                    alumno.setPuntaje(alumno.getPuntaje() + ptsDiff);
                                    alumnoService.update(alumno);
                                }
                            }
                        }
                    }
                }
                
               return new ResponseEntity<Void>(HttpStatus.CREATED); 
            }    
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

    }
    
    @Override
    protected void checkCreate() {
    }

    @Override
    protected void checkUpdate() {
    }

    @Override
    protected void checkDelete() {
    }

    private boolean camposCorrectos(Pregunta obj) {
        //campos no pueden ser vacios
        //la pregunta debe ser inicial o tener alguna previa
        //la pregunta no puede ser inicial y tener previas
        boolean preguntasPreviasVacias = obj.getPreguntasPrevias() == null || obj.getPreguntasPrevias().isEmpty();
        boolean retVal = 
                StringUtils.hasText(obj.getTitulo())
                && obj.getDescripcion() != null
                && StringUtils.hasText(obj.getDescripcion().getTexto())
                && StringUtils.hasText(obj.getRespuesta())
                && obj.getTema() != null 
                && obj.getTip() != null
                && StringUtils.hasText(obj.getTip().getTexto())
                && ((obj.isInicial() && preguntasPreviasVacias) 
                    || (!obj.isInicial() && !preguntasPreviasVacias));
        return retVal;
    }
 
    /**
     * 
     * @param id
     * @param ucBuilder
     * @param authorizationToken
     * @return 
     */
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id, UriComponentsBuilder ucBuilder,
                                       @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken
    )
    {
        Pregunta pregunta = (Pregunta) getService().findById(id);
        
        //validaciones
        // que exista
        if (pregunta == null) {
            throw new EntityNotFoundException("No se encuentra la entidad con id='" + id + "'");
        }
        // que no tenga preguntas que desbloquea
        if (!pregunta.getPreguntasQueDesbloquea().isEmpty()) {
            throw new EntityHasAssociationException("La pregunta tiene preguntas que desbloquea, no se puede borrar.");
        }
        
        //borrar las PreguntaAlumno
        for (Alumno alumno : alumnoService.findAll())
        {
            PreguntaAlumno preguntaAlumnoFound = null;
            for (PreguntaAlumno preguntaAlumno : new ArrayList<PreguntaAlumno>(alumno.getPreguntasAlumno().values()))
            {
                if (preguntaAlumno.getPreguntaId().equals(pregunta.getId()))
                {
                    preguntaAlumnoFound = preguntaAlumno;
                    alumno.getPreguntasAlumno().remove(pregunta.getId());
                }
            }
            if (preguntaAlumnoFound != null)
            {
                // se actualizan las estadisticas del alumno, se puede sacar, le puede no gustar perder puntos.
                if (preguntaAlumnoFound.isRespuestaCorrecta())
                {
                    alumno.setPuntaje(alumno.getPuntaje() - pregunta.getPuntos());
                    alumno.setCantidadPreguntasCorrectas(alumno.getCantidadPreguntasCorrectas() - 1);
                }
                alumno.setCantidadPreguntasIncorrectas(
                        alumno.getCantidadPreguntasIncorrectas() - preguntaAlumnoFound.getRespuestaIncorrecta());
                alumnoService.update(alumno);
            }
        }
        
        //borrar los Error
        for (Error error : errorService.findErrorByPregunta(pregunta))
        {
            errorService.delete(error);
        }
        
        getService().deleteId(id);
        
        //buscamos todas las preguntas que desbloquean a esta, y borramos la relacion
        for (Pregunta preguntaItem : preguntaService.getPreguntasTema(pregunta.getTema().getId())) 
        {
            if (preguntaItem.getPreguntasQueDesbloquea().contains(id)) 
            {
                preguntaItem.getPreguntasQueDesbloquea().remove(id);
                preguntaService.update(preguntaItem);
            }
        }
        
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
   
}