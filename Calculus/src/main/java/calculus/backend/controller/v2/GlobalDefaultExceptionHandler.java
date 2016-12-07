/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.controller.v2;

import calculus.backend.exception.BadCredentialsException;
import calculus.backend.exception.EntityHasAssociationException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Esta clase maneja las respuestas a todas las excepciones de la app.
 * @author abentan
 */
@ControllerAdvice
class GlobalDefaultExceptionHandler
{
    private static final Logger LOG = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * Aca se manejan todas las excepciones genericas, que son disparadas por los servicios y no catcheadas.
     * @param req
     * @param ex
     * @return
     * @throws Exception 
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo defaultErrorHandler(HttpServletRequest req, Exception ex) 
            throws Exception
    {
        LOG.info("public ErrorInfo defaultErrorHandler(HttpServletRequest req, Exception ex)");
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null)
        {
            LOG.info("Exception rethrown:"  + ex.getClass().getName());
            throw ex;
        }
        
        LOG.error(ex.getMessage(), ex);
        return new ErrorInfo(ex);
    }

    /**
     * Por ahora dejamos NOT_FOUND, pero debería ser otro, para distinguir cuando esta mal la url, por ej. HttpStatus.METHOD_NOT_ALLOWED.
     * @param req
     * @param ex
     * @return
     * @throws Exception 
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ErrorInfo entityNotFoundExceptionHandler(HttpServletRequest req, EntityNotFoundException ex) 
            throws Exception
    {
        LOG.debug("public ErrorInfo entityNotFoundExceptionHandler(HttpServletRequest req, Exception ex)");
        LOG.debug(ex.getMessage(), ex);
        return new ErrorInfo(ex);
    }
    
    /**
     * Devuelve 406 en este caso.
     * @param req
     * @param ex
     * @return
     * @throws Exception 
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityHasAssociationException.class)
    @ResponseBody
    public ErrorInfo entityHasAssociationExceptionHandler(HttpServletRequest req, EntityHasAssociationException ex) 
            throws Exception
    {
        LOG.debug("public ErrorInfo entityHasAssociationExceptionHandler(HttpServletRequest req, Exception ex)");
        LOG.debug(ex.getMessage(), ex);
        return new ErrorInfo(ex);
    }
    
    /**
     * Por ahora mapeamos BadCredentialsException a HttpStatus.UNAUTHORIZED.
     * @param req
     * @param ex
     * @return
     * @throws Exception 
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ErrorInfo badCredentialsExceptionHandler(HttpServletRequest req, BadCredentialsException ex) 
            throws Exception
    {
        LOG.debug("public ErrorInfo badCredentialsExceptionHandler(HttpServletRequest req, Exception ex)");
        LOG.debug(ex.getMessage(), ex);
        return new ErrorInfo(ex);
    }
    
    /**
     * Clase auxiliar para guardar info de respuesta.
     */
    public static class ErrorInfo
    {
        public final String message;

        public ErrorInfo(Exception ex)
        {
            this.message = ex.getLocalizedMessage();
        }
    }
}
