package de.minimum.hawapp.server.blackboard.api;

import java.util.List;

import de.minimum.hawapp.server.blackboard.util.OfferCreationStatus;

public interface BlackboardManager {

    OfferCreationStatus createOffer(String category, String header, String description, String contact, double price,
                    byte[] image);

    boolean removeOffer(long offerId, String deletionKey);

    void reportOffer(long offerId, String reason);

    List<Offer> getAllOffers();

    Offer getOffer(long offerId);

    Category getCategory(String category);

    List<Offer> getOffersBySearchStr(String searchStr);

    Category getOffersBySearchStrAndCategory(String searchStr, String category);

    List<Category> getAllCategories();

    List<String> getAllCategoryNames();

    Image getImage(long imageId);
}
