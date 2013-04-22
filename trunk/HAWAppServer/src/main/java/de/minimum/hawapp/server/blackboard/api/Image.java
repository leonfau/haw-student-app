package de.minimum.hawapp.server.blackboard.api;

public interface Image extends Persistent<Image> {

    long getId();

    byte[] getImage();
}
