package de.minimum.hawapp.app.blackboard.beans;

import java.util.Date;

import de.minimum.hawapp.app.blackboard.api.OfferCreationStatus;

public class OfferCreationStatusBean implements OfferCreationStatus {
    private String deletionKey;
    private boolean successfull;
    private long offerId;
    private Date dateOfCreation;

    @Override
    public String getDeletionKey() {
        return this.deletionKey;
    }

    @Override
    public boolean isSuccessfull() {
        return this.successfull;
    }

    @Override
    public long getOfferId() {
        return this.offerId;
    }

    @Override
    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    public void setDeletionKey(String deletionKey) {
        this.deletionKey = deletionKey;
    }

    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

}
