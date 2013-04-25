package de.minimum.hawapp.server.blackboard.exceptions;

public class PersistenceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2378105278634445860L;

    public PersistenceException(Throwable throwable) {
        super(throwable);
    }

    public PersistenceException(String msg) {
        super(msg);
    }

    public PersistenceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
