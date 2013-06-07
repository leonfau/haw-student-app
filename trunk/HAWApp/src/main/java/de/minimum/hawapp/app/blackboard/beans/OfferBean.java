package de.minimum.hawapp.app.blackboard.beans;

import java.util.Date;

import de.minimum.hawapp.app.blackboard.api.Offer;

public class OfferBean implements Offer {
    private long id = -1;
    private String header;
    private String description;
    private String contact;
    private Date dateOfCreation;
    private Long imageId;
    private String categoryName;

    public OfferBean() {
    }

    public OfferBean(String header, String description, String contact, String categoryName) {
        this.header = header;
        this.description = description;
        this.contact = contact;
        this.categoryName = categoryName;
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
    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    @Override
    public Long getImageId() {
        return this.imageId;
    }

    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

    /**
     * @param id
     *            the id to set
     */
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

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.categoryName == null) ? 0 : this.categoryName.hashCode());
        result = prime * result + ((this.contact == null) ? 0 : this.contact.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.header == null) ? 0 : this.header.hashCode());
        result = prime * result + (int)(this.id ^ (this.id >>> 32));
        result = prime * result + ((this.imageId == null) ? 0 : this.imageId.hashCode());
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
        OfferBean other = (OfferBean)obj;
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
        if (this.imageId == null) {
            if (other.imageId != null)
                return false;
        }
        else if (!this.imageId.equals(other.imageId))
            return false;
        return true;
    }

}
