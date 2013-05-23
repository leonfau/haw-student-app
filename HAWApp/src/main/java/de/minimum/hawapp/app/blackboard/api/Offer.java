package de.minimum.hawapp.app.blackboard.api;

import java.util.Date;

public interface Offer {
    long getId();

    String getHeader();

    String getDescription();

    String getContact();

    Date getDateOfCreation();

    Long getImageId();

    String getCategoryName();

}
