package calculus.backend.model;

import calculus.backend.View;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class Informacion implements Serializable {
    
    @JsonView(View.All.class)
    @Basic
    private String texto;
    
    @JsonView(View.All.class)
    @Basic
    private String imagen;
    
    @JsonView(View.All.class)
    @Basic
    private String video;
    
    public Informacion() {
    }
    
    public Informacion(String texto, String imagen) {
        this.texto = texto;
        this.imagen = imagen;
    }
    
    public String getTexto() {
        return texto;
    }
    
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the video
     */
    public String getVideo()
    {
        return video;
    }

    /**
     * @param video the video to set
     */
    public void setVideo(String video)
    {
        this.video = video;
    }
    
    /**
     * Devolvemos el video embebido para utilizarlo mas facilment en el juego.
     * @return 
     */
    @JsonView(View.All.class)
    public String getEmbeddedVideo()
    {
        if (video == null || video.length() == 0)
        {
            return video;
        }
        int eqIndex = video.lastIndexOf("=");
        return "https://www.youtube.com/embed/" + video.substring(eqIndex + 1);
    }
    
}