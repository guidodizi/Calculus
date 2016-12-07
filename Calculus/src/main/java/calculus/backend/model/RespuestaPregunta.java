/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.model;

/**
 *
 * @author tavidian
 */
public class RespuestaPregunta {
    private boolean correcto;
    private int puntosGanadosEnEstaPregunta;
    private int PuntosTotal;

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public int getPuntosGanadosEnEstaPregunta() {
        return puntosGanadosEnEstaPregunta;
    }

    public void setPuntosGanadosEnEstaPregunta(int puntosGanadosEnEstaPregunta) {
        this.puntosGanadosEnEstaPregunta = puntosGanadosEnEstaPregunta;
    }

    public int getPuntosTotal() {
        return PuntosTotal;
    }

    public void setPuntosTotal(int PuntosTotal) {
        this.PuntosTotal = PuntosTotal;
    }
    
    
    
}
