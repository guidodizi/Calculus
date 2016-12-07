/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend;

import calculus.backend.controller.v2.admin.AbstractController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * TODO: ponerlo en el contexto de Spring y algunas cosas deberian ser propiedades.
 *
 * @author abentan
 */
public class GeneralUtil
{

    public static final String REST_SERVICE_BASE_URI = "http://localhost:8084/CalculusBackend";

    /**
     *
     * @param location
     * @return
     * @throws NumberFormatException
     */
    public static Long getEntityIdFromLocation(String location)
            throws NumberFormatException
    {
        return Long.parseLong(location.substring(location.lastIndexOf("/") + 1));
    }

    /**
     * Hace un RestTemplate.getForObject pasandole el token de autorizacion.
     * @param <T>
     * @param restTemplate
     * @param uri
     * @param clazz
     * @param authorizationValue
     * @return
     */
    public static <T> T getForObject(RestTemplate restTemplate, String uri, Class<T> clazz, String authorizationValue)
    {
        T retVal;
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(AbstractController.AUTHORIZATION, authorizationValue);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);
        ResponseEntity<T> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, clazz);
        retVal = response.getBody();
        return retVal;
    }

    /**
     * Hace un RestTemplate.postForObject pasandole el token de autorizacion.
     * @param <T>
     * @param restTemplate
     * @param url
     * @param entity
     * @param clazz
     * @param authorization
     * @return 
     */
    public static <T> ResponseEntity<T> postForObject(RestTemplate restTemplate, String url, T entity, Class<T> clazz, String authorization)
    {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.set(AbstractController.AUTHORIZATION, authorization);
        HttpEntity<?> requestEntity = new HttpEntity<T>(entity,multiValueMap);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, clazz);
        return responseEntity;
    }

    /**
     * Hace un RestTemplate.putForObject pasandole el token de autorizacion.
     * @param <T>
     * @param restTemplate
     * @param url
     * @param entity
     * @param clazz
     * @param authorization
     * @return 
     */
    public static <T> ResponseEntity<?> putForObject(RestTemplate restTemplate, String url, T entity, Class<T> clazz, String authorization)
    {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.set(AbstractController.AUTHORIZATION, authorization);
        HttpEntity<?> requestEntity = new HttpEntity<T>(entity,multiValueMap);
        ResponseEntity<?> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, clazz);
        return responseEntity;
    }

}
