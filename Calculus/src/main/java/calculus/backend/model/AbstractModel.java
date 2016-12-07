package calculus.backend.model;

import java.io.Serializable;

/**
 * Clase base de las entidades en la BDs.
 * 
 */
public abstract class AbstractModel implements Serializable
{
    public abstract Long getId();
    
    public abstract void setId(Long id);
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractModel other = (AbstractModel) obj;
        if (this.getId() == null)
            return false;
        if (other.getId() == null)
            return false;
        if (getId().longValue() != other.getId().longValue())
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + ( getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }
    
}