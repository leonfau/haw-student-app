package de.minimum.hawapp.server.persistence.blackboard;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import de.minimum.hawapp.server.blackboard.api.Image;

@Entity
@Table(name = "Blackboard_Image", catalog = "haw_app")
public class ImageEntity implements Image {

    /**
     * 
     */
    private static final long serialVersionUID = -5657084710401175143L;
    private byte[] image;
    private long id = -1;

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Column(name = "image")
    @Override
    public byte[] getImage() {
        return this.image;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public long getId() {
        return this.id;
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
        ImageEntity other = (ImageEntity)obj;
        if (this.id != other.id)
            return false;
        if (!Arrays.equals(this.image, other.image))
            return false;
        return true;
    }

    @Override
    public boolean equalKey(Image o) {
        return this.id == o.getId();
    }
}
