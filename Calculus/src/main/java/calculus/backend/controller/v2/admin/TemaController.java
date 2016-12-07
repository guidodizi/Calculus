package calculus.backend.controller.v2.admin;

import static calculus.backend.controller.v2.admin.AbstractController.AUTHORIZATION;
import calculus.backend.exception.EntityHasAssociationException;
import calculus.backend.model.Pregunta;
import calculus.backend.model.Profesor;
import calculus.backend.model.Tema;
import calculus.backend.service.AbstractService;
import calculus.backend.service.PreguntaService;
import calculus.backend.service.SessionService;
import calculus.backend.service.TemaService;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("v2AdminTemaController")
@RequestMapping(value = TemaController.URI_PREFIX)
public class TemaController extends AbstractController<Tema> {
    
    public static final String URI_PREFIX = "/v2/admin/temas";
    
    private static final Logger LOG = LoggerFactory.getLogger(TemaController.class);
    
    @Autowired
    protected TemaService service;
    @Autowired
            PreguntaService preguntaService;
    @Autowired
        SessionService sessionService;
    
    @RequestMapping(value = "/{tema}/preguntas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPreguntasAlumnoTema(
            @PathVariable("tema") long idTema,
            @RequestHeader(value="Authorization", required = false) String token_key
    ) {
        
        Profesor p = (Profesor) sessionService.login(token_key);        
        return new ResponseEntity<Set<Pregunta>>(preguntaService.getPreguntasTema(idTema),HttpStatus.OK);
        
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    @Override
    public ResponseEntity<?> create(@RequestBody Tema obj, UriComponentsBuilder ucBuilder,
                                    @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken) {
        Profesor p = (Profesor) sessionService.login(authorizationToken);      
        Tema t1 = service.findByTitulo(obj.getTema());
        if (t1 != null) {
            return new ResponseEntity<String>("Ya existe un tema con ese titulo.", HttpStatus.FORBIDDEN );
        } else {
            Tema t = service.create(obj);
        
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(t.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        }
    }
    
    @Override
    protected AbstractService getService() {
        return this.service;
    }
    
    @Override
    protected String getMapString() {
        return URI_PREFIX;
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
    
    @Override
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Tema obj, UriComponentsBuilder ucBuilder,
                                    @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken) {
        Profesor p = (Profesor) sessionService.login(authorizationToken);
        Tema t1 = service.findByTitulo(obj.getTema());
        if (t1 != null && !t1.getId().equals(obj.getId())) {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        } else {
            Tema t = service.update(obj);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
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
        // validaciones
        // que exista
        if (!getService().existsId(id)) {
            throw new EntityNotFoundException("No se encuentra la entidad con id='" + id + "'");
        }
        // que no tenga preguntas asociadas, TODO: esto se podría mejorar con una consulta SQL
        for (Pregunta pregunta : preguntaService.findAll()) {
            if (pregunta.getTema().getId().equals(id)) {
                throw new EntityHasAssociationException("El tema tiene preguntas asociadas, no se puede borrar.");
            }
        }
        
        getService().deleteId(id);
        
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

}