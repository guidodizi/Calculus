package calculus.backend.controller.v2.admin;

import calculus.backend.model.AbstractModel;
import calculus.backend.service.AbstractService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * Clase base de los controladores.
 * @param <T> 
 */
@RestController("v2AdminAbstractController")
public abstract class AbstractController<T extends AbstractModel> {
    
    public static final String AUTHORIZATION = "Authorization";
    
    protected abstract AbstractService getService();
    protected abstract String getMapString();
    
    protected abstract void checkCreate();
    protected abstract void checkUpdate();
    protected abstract void checkDelete();
    
    /**
     * 
     * @param authorizationToken
     * @return 
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<T>> findAll(
            @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken)
    {
        List<T> list = getService().findAll();
        if (list == null)
        {
            list = new ArrayList<T>();
        }
        
        return new ResponseEntity<List<T>>(list, HttpStatus.OK);
    }
    
    /**
     * 
     * @param id
     * @param authorizationToken
     * @return 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<T> findById(@PathVariable("id") long id,
                                      @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken)
    {
        T obj = (T) getService().findById(id);
        if (obj == null)
        {
            throw new EntityNotFoundException("No se encuentra la entidad con id='" + obj.getId() + "'");
        }
        return new ResponseEntity<T>(obj, HttpStatus.OK);
    }
    

    /**
     * 
     * @param obj
     * @param ucBuilder
     * @param authorizationToken
     * @return 
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody T obj, UriComponentsBuilder ucBuilder,
                                    @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken
    )
    {
        Assert.notNull(obj, "El 'body' no puede estar vacío.");
        
        checkCreate();
        
        getService().create(obj);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(obj.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    

    /**
     * 
     * @param obj
     * @param ucBuilder
     * @param authorizationToken
     * @return 
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody T obj, UriComponentsBuilder ucBuilder,
                                       @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken
    )
    {
        Assert.notNull(obj, "El 'body' no puede estar vacío.");
        Assert.notNull(obj.getId(), "El 'body' debe contener un elemento 'id'.");
        
        checkUpdate();
        
        if (!getService().existsId(obj.getId())) { // no debería devolver lo mismo que not_found, en get?
            throw new EntityNotFoundException("No se encuentra la entidad con id='" + obj.getId() + "'");
        }
        
        getService().update(obj);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path(getMapString() + "/{id}").buildAndExpand(obj.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    /**
     * 
     * @param id
     * @param ucBuilder
     * @param authorizationToken
     * @return 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") long id, UriComponentsBuilder ucBuilder,
                                       @RequestHeader(value = AUTHORIZATION, required = false) String authorizationToken
    )
    {
        checkDelete();
        
        if (!getService().existsId(id)) {
            throw new EntityNotFoundException("No se encuentra la entidad con id='" + id + "'");
        }
        
        getService().deleteId(id);
        
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

}