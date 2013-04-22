package de.minimum.hawapp.server.blackboard.api;

import java.util.List;

public interface Category extends Persistent<Category> {

    void addOffer(Offer offer);

    boolean removeOffer(Offer offer);

    boolean removeOffer(long offerId);

    String getName();

    List<Offer> getAllOffers();
}
