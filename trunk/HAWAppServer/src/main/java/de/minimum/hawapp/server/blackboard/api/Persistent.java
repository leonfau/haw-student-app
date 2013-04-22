package de.minimum.hawapp.server.blackboard.api;

import java.io.Serializable;

public interface Persistent<T extends Persistent<T>> extends Serializable {

    boolean equalKey(T o);
}
