package de.minimum.hawapp.app.rest.exceptions;

public class RestServiceException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -8706674986042798808L;

    public RestServiceException() {
    }

    public RestServiceException(String detailMessage) {
        super(detailMessage);
    }

    public RestServiceException(Throwable throwable) {
        super(throwable);
    }

    public RestServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
