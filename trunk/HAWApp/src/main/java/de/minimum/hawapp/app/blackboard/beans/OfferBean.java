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

}
