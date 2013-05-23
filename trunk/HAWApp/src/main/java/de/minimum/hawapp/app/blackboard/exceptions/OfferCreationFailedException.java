package de.minimum.hawapp.app.blackboard.exceptions;

public class OfferCreationFailedException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2801329187108108644L;

    public OfferCreationFailedException(Throwable tr) {
        super(tr);
    }

    public OfferCreationFailedException(String msg, Throwable tr) {
        super(msg, tr);
    }

    public OfferCreationFailedException(String msg) {
        super(msg);
    }

}
