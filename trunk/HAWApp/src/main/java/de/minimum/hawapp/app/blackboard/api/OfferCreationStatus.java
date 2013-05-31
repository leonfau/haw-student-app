package de.minimum.hawapp.app.blackboard.api;

import java.util.Date;

public interface OfferCreationStatus {
    public String getDeletionKey();

    public boolean isSuccessfull();

    public long getOfferId();

    public Date getDateOfCreation();
}
