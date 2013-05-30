package de.minimum.hawapp.server.blackboard.util;

import java.util.Date;

public class OfferCreationStatus {

    private boolean successfull;
    private long offerId;
    private String deletionKey;
    private Date dateOfCreation;

    public OfferCreationStatus(long offerId, boolean successfull, String deletionKey, Date dateOfCreation) {
        this.offerId = offerId;
        this.successfull = successfull;
        this.deletionKey = deletionKey;
        this.dateOfCreation = dateOfCreation;
    }

    public boolean isSuccessfull() {
        return this.successfull;
    }

    public long getOfferId() {
        return this.offerId;
    }

    public String getDeletionKey() {
        return this.deletionKey;
    }

    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }
}
