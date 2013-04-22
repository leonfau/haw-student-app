package de.minimum.hawapp.server.blackboard.util;

public class OfferCreationStatus {

    private boolean successfull;
    private long offerId;
    private String deletionKey;

    public OfferCreationStatus(long offerId, boolean successfull, String deletionKey) {
        this.offerId = offerId;
        this.successfull = successfull;
        this.deletionKey = deletionKey;
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
}
