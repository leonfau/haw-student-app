package de.minimum.hawapp.server.blackboard.api;

public interface Report extends Persistent<Report> {

    long getId();

    String getReason();

    Offer getOffer();
}
