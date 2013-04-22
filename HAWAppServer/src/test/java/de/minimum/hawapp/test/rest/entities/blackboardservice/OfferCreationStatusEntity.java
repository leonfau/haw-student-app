package de.minimum.hawapp.test.rest.entities.blackboardservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OfferCreationStatusEntity {

    private boolean successfull;
    private long offerId;
    private String deletionKey;

    public boolean isSuccessfull() {
        return this.successfull;
    }

    public long getOfferId() {
        return this.offerId;
    }

    public String getDeletionKey() {
        return this.deletionKey;
    }

    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }

    public void setOfferId(long offerId) {
        this.offerId = offerId;
    }

    public void setDeletionKey(String deletionKey) {
        this.deletionKey = deletionKey;
    }
}
