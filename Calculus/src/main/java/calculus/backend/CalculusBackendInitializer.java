package calculus.backend;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class CalculusBackendInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    
    private static final Logger LOG = LoggerFactory.getLogger(CalculusBackendInitializer.class);
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { CalculusBackendConfiguration.class, JpaConfig.class };
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
    
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
    
    @Override
    protected Filter[] getServletFilters()
    {
        Filter[] singleton =
        {
            new CORSFilter()
        };
        return singleton;
    }
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException
    {
        super.onStartup(servletContext);
        LOG.info("Arrancando CalculusBackend ... " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

}