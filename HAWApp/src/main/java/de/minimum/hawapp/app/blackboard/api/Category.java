package de.minimum.hawapp.app.blackboard.api;

import java.util.List;

public interface Category {

    String getName();

    List<Offer> getAllOffers();

    void ignoreOffers(List<Long> toIgnore);

    void unignoreOffer(long offerId);
}
