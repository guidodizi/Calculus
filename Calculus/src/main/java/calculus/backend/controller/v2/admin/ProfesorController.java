package calculus.backend.controller.v2.admin;

import calculus.backend.model.Profesor;
import calculus.backend.service.AbstractService;
import calculus.backend.service.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("v2AdminProfesorController")
@RequestMapping(value = ProfesorController.URI_PREFIX)
public class ProfesorController extends AbstractController<Profesor>{
    
    public static final String URI_PREFIX = "/v2/admin/profesores";
    
    @Autowired
    protected ProfesorService service;
    
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
    
}