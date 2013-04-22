package de.minimum.hawapp.test.rest.entities.blackboardservice;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfferEntity {

    private long id;
    private String header;
    private String description;
    private String contact;
    private double price;
    private Date dateOfCreation;
    private long imageId;
    private String categoryName;

    public void setId(long id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return this.id;
    }

    public String getHeader() {
        return this.header;
    }

    public String getDescription() {
        return this.description;
    }

    public String getContact() {
        return this.contact;
    }

    public double getPrice() {
        return this.price;
    }

    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    public long getImageId() {
        return this.imageId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.categoryName == null) ? 0 : this.categoryName.hashCode());
        result = prime * result + ((this.contact == null) ? 0 : this.contact.hashCode());
        result = prime * result + ((this.dateOfCreation == null) ? 0 : this.dateOfCreation.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.header == null) ? 0 : this.header.hashCode());
        result = prime * result + (int)(this.id ^ (this.id >>> 32));
        result = prime * result + (int)(this.imageId ^ (this.imageId >>> 32));
        long temp;
        temp = Double.doubleToLongBits(this.price);
        result = prime * result + (int)(temp ^ (temp >>> 32));
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
        OfferEntity other = (OfferEntity)obj;
        if (this.categoryName == null) {
            if (other.categoryName != null)
                return false;
        }
        else if (!this.categoryName.equals(other.categoryName))
            return false;
        if (this.contact == null) {
            if (other.contact != null)
                return false;
        }
        else if (!this.contact.equals(other.contact))
            return false;
        if (this.dateOfCreation == null) {
            if (other.dateOfCreation != null)
                return false;
        }
        else if (!this.dateOfCreation.equals(other.dateOfCreation))
            return false;
        if (this.description == null) {
            if (other.description != null)
                return false;
        }
        else if (!this.description.equals(other.description))
            return false;
        if (this.header == null) {
            if (other.header != null)
                return false;
        }
        else if (!this.header.equals(other.header))
            return false;
        if (this.id != other.id)
            return false;
        if (this.imageId != other.imageId)
            return false;
        if (Double.doubleToLongBits(this.price) != Double.doubleToLongBits(other.price))
            return false;
        return true;
    }

}
