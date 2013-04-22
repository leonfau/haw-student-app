package de.minimum.hawapp.test.rest.entities.blackboardservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImageEntity {
    private byte[] image;
    private long id;

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
}
