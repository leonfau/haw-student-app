package de.minimum.hawapp.server.blackboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minimum.hawapp.server.blackboard.api.BlackboardManager;
import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.util.BlackboardFactoryManager;
import de.minimum.hawapp.server.blackboard.util.OfferCreationStatus;

//TODO Cachinggröße -> Clockalgorithmus zum verwalten?
public class CachingBlackboardManager implements BlackboardManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlackboardManager.class);

    private static final OfferCreationStatus FAILED_CREATION = new OfferCreationStatus(-1, false, "");
    private Map<String, Category> categories = new HashMap<String, Category>();
    private Map<Long, Offer> offers = new HashMap<>();
    private Map<Long, Image> images = new HashMap<>();

    public CachingBlackboardManager() {
        this.categories.put("Angebote", BlackboardFactoryManager.newCategory("Angebote"));// TODO
                                                                                          // aus
                                                                                          // Datenbank
                                                                                          // beziehen
    }

    @Override
    public OfferCreationStatus createOffer(String category, String header, String description, String contact,
                    double price, byte[] image) {
        if (!checkRequirements(category, header))
            return CachingBlackboardManager.FAILED_CREATION;
        Category cat = this.categories.get(category);
        if (cat == null) {
            // TODO erstmal in DB nachschauen und falls ja zur Map adden ->
            // Clock???
            return CachingBlackboardManager.FAILED_CREATION;
        }
        Image img = BlackboardFactoryManager.newImage(image);
        // TODO erstmal in DB damit Id auch vorhanden
        this.images.put(img.getId(), img);
        Offer offer = BlackboardFactoryManager.newOffer(cat, header, description, contact, price, new Date(),
                        img.getId());
        // TODO offer in DB damit id vorhanden
        this.offers.put(offer.getId(), offer);
        return new OfferCreationStatus(offer.getId(), true, offer.getDeletionKey());
    }

    private boolean checkRequirements(String category, String header) {
        return category != null && header != null;
    }

    @Override
    public boolean removeOffer(long offerId, String deletionKey) {
        Offer offer = getOffer(offerId);
        if (offer == null || !checkDeletionKey(offer, deletionKey)) {
            return false;
        }
        offer.getCategory().removeOffer(offer);
        // TODO evtl. archivieren?
        this.images.remove(offer.getImageId());// TODO auch aus DB löschen
        this.offers.remove(offer.getId());// TODO auch aus DB löschen
        return true;
    }

    private boolean checkDeletionKey(Offer toDelete, String deletionKey) {
        return toDelete.getDeletionKey().equals(deletionKey);
    }

    @Override
    public void reportOffer(long offerId, String reason) {
        CachingBlackboardManager.LOGGER.info("Reported Offer: " + offerId + "; reason: " + reason);
        // TODO Auto-generated method stub

    }

    @Override
    public List<Offer> getAllOffers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Offer getOffer(long offerId) {
        return this.offers.get(offerId);// TODO falls nicht vorhanden in DB
                                        // schauen und ggf in Map hinzufügen ->
                                        // Clock???
    }

    @Override
    public Category getCategory(String category) {
        return this.categories.get(category);// TODO falls nicht vorhanden in DB
                                             // schauen und ggf in Map
                                             // hinzufügen -> Clock???
    }

    @Override
    public List<Offer> getOffersBySearchStr(String searchStr) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Offer> getOffersBySearchStrAndCategory(String searchStr, String category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getAllCategoryNames() {
        List<String> names = new ArrayList<>();
        for(Category cat : this.categories.values()) {
            names.add(cat.getName());
        }
        // TODO auch in Datenbank nachschauen
        return names;
    }

    @Override
    public Image getImage(long imageId) {
        return this.images.get(imageId);// TODO falls nicht vorhanden in DB
                                        // schauen und ggf in Map hinzufügen ->
                                        // Clock???
    }
}
