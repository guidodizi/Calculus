/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculus.backend.exception;

/**
 * Disparada cuando cuando se quiere borrar una entidad, y no se puede porque la misma tiene otras entidades 
 * asociadas.
 * @author abentan
 */
public class EntityHasAssociationException extends RuntimeException
{

    /**
     * Creates a new instance of <code>WrongPasswordException</code> without detail message.
     */
    public EntityHasAssociationException()
    {
    }

    /**
     * Constructs an instance of <code>WrongPasswordException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public EntityHasAssociationException(String msg)
    {
        super(msg);
    }
}
