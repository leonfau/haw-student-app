package de.minimum.hawapp.server.blackboard.util;

public class PersistenceResult<T> {

    private T persistenceObject;
    private boolean successfull;

    public PersistenceResult(T persistenceObject, boolean successfull) {
        this.persistenceObject = persistenceObject;
        this.successfull = successfull;
    }

    public boolean isSuccessfull() {
        return this.successfull;
    }

    public T getPersistenceObject() {
        return this.persistenceObject;
    }
}
