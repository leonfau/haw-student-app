package de.minimum.hawapp.app.blackboard.beans;

import java.util.Arrays;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(this.id ^ (this.id >>> 32));
        result = prime * result + Arrays.hashCode(this.image);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImageBean other = (ImageBean)obj;
        if (this.id != other.id)
            return false;
        if (!Arrays.equals(this.image, other.image))
            return false;
        return true;
    }

}
