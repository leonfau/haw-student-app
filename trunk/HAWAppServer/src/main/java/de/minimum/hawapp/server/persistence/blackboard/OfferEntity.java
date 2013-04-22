package de.minimum.hawapp.server.persistence.blackboard;

import java.util.Date;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Offer;

//TODO kommt die DB mit der rekursiven Strunktur zurecht: Offer -> Category -> Offer -> usw.
public class OfferEntity implements Offer {

    /**
     * 
     */
    private static final long serialVersionUID = 3580355548763084727L;
    private long id;
    private String header;
    private String description;
    private String contact;
    private double price;
    private Date dateOfCreation;
    private long imageId;
    private String deletionKey;
    private Category category;

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

    public void setDeletionKey(String deletionKey) {
        this.deletionKey = deletionKey;
    }

    @Override
    public String getDeletionKey() {
        return this.deletionKey;
    }

    public void setCategory(Category category) {
        if (this.category != null)
            this.category.removeOffer(this);
        this.category = category;
        category.addOffer(this);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getHeader() {
        return this.header;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getContact() {
        return this.contact;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    @Override
    public long getImageId() {
        return this.imageId;
    }

    @Override
    public String getCategoryName() {
        return this.category.getName();
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public boolean equalKey(Offer o) {
        return this.id == o.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
        result = prime * result + ((this.contact == null) ? 0 : this.contact.hashCode());
        result = prime * result + ((this.dateOfCreation == null) ? 0 : this.dateOfCreation.hashCode());
        result = prime * result + ((this.deletionKey == null) ? 0 : this.deletionKey.hashCode());
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
        if (this.category == null) {
            if (other.category != null)
                return false;
        }
        else if (!this.category.equals(other.category))
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
        if (this.deletionKey == null) {
            if (other.deletionKey != null)
                return false;
        }
        else if (!this.deletionKey.equals(other.deletionKey))
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
