package de.minimum.hawapp.app.blackboard.api;

import java.util.List;

public interface Category {

    String getName();

    List<Offer> getAllOffers();
}
