package de.minimum.hawapp.server.blackboard.api;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public interface Offer extends Persistent<Offer> {

    long getId();

    String getHeader();

    String getDescription();

    String getContact();

    Date getDateOfCreation();

    Long getImageId();

    @JsonIgnore
    Category getCategory();

    String getCategoryName();

    @JsonIgnore
    String getDeletionKey();
}
