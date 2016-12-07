/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.model;

import java.util.List;
import java.util.Set;

/**
 *
 * @author tavidian
 */
public class EstadisticasDashboard {
    
    private long cantidad_alumnos;
    private long cantidad_dudas;
    private long cantidad_respuestas_correctas;
    private long cantidad_respuestas;
    private Set<Pregunta> preguntas_dificiles;

    public EstadisticasDashboard(long cantRespuestasCorrectas, long cantRespuestas, Set<Pregunta> preguntasDificiles, long count_dudas, long count_alumnos) {
        this.cantidad_respuestas_correctas = cantRespuestasCorrectas;
        this.cantidad_respuestas = cantRespuestas;
        this.cantidad_alumnos = count_alumnos;
        this.cantidad_dudas = count_dudas;
        this.preguntas_dificiles = preguntasDificiles;
    }
    
    
    
    public long getCantidadAlumnos() {
        return cantidad_alumnos;
    }
    
    public long getCantidadDudas() {
        return cantidad_dudas;
    }
    
    public long getCantidadRespuestas() {
        return this.cantidad_respuestas;
    }
    
    public float getProcentajeRespuestasCorrectas() {
        return (float) ((float)this.cantidad_respuestas_correctas / this.cantidad_respuestas * 100);
    }
    
    public Set<Pregunta> getPreguntasDificiles() {
        return this.preguntas_dificiles;
    }
    
    
}
