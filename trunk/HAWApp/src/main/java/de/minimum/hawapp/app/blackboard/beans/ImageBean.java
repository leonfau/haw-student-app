package de.minimum.hawapp.app.blackboard.beans;

import de.minimum.hawapp.app.blackboard.api.Image;

public class ImageBean implements Image {
    private long id = -1;
    private byte[] image;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
