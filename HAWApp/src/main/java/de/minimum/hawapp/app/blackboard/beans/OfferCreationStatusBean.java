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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.deletionKey == null) ? 0 : this.deletionKey.hashCode());
        result = prime * result + (int)(this.offerId ^ (this.offerId >>> 32));
        result = prime * result + (this.successfull ? 1231 : 1237);
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
        OfferCreationStatusBean other = (OfferCreationStatusBean)obj;
        if (this.deletionKey == null) {
            if (other.deletionKey != null)
                return false;
        }
        else if (!this.deletionKey.equals(other.deletionKey))
            return false;
        if (this.offerId != other.offerId)
            return false;
        if (this.successfull != other.successfull)
            return false;
        return true;
    }

}
