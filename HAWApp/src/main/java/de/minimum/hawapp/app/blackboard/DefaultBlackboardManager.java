package de.minimum.hawapp.app.blackboard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.blackboard.api.Category;
import de.minimum.hawapp.app.blackboard.api.Image;
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.blackboard.api.OfferCreationStatus;
import de.minimum.hawapp.app.blackboard.beans.OfferBean;
import de.minimum.hawapp.app.blackboard.exceptions.OfferCreationFailedException;
import de.minimum.hawapp.app.rest.BlackboardService;

//TODO regelmäßig überprüfen ob auch schon sehr alte eigene noch im Speicher liegen die entfernt werden müssen
public class DefaultBlackboardManager implements BlackboardManager {
    private BlackboardService bbService = new BlackboardService();

    private Map<Long, String> ownOffersToDelKey = new HashMap<Long, String>();
    private Set<Long> ignoredOffers = new HashSet<Long>();
    private Context context = null;// Beim benutzen immer auf Null Checken!!!

    public DefaultBlackboardManager() {
        // TODO eigene Offer aus dem Speicher holen
    }

    @Override
    public List<Offer> getAllOwnOffers() {
        List<Offer> ownOffers = new ArrayList<Offer>();
        for(Long offerId : this.ownOffersToDelKey.keySet()) {
            ownOffers.add(getOfferById(offerId));// TODO nett wäre eine
                                                 // restSchnittstelle für
                                                 // mehrere Ids.... / evtl. in
                                                 // Liste ablegen...
        }
        return ownOffers;
    }

    @Override
    public boolean removeOwnOffer(Offer offer) {// TODO auch aus dem Speicher
                                                // löschen
        String delKey = this.ownOffersToDelKey.get(offer.getId());
        if (delKey == null)
            return false;
        boolean removed = this.bbService.removeOffer(offer, delKey);
        if (removed) {
            this.ownOffersToDelKey.remove(offer.getId());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean ignoreOffer(Offer offer) {
        this.ignoredOffers.add(offer.getId());
        return true;
    }

    @Override
    public boolean unignoreOffer(Offer offer) {
        return this.ignoredOffers.remove(offer.getId());
    }

    @Override
    public List<Offer> getIgnoredOffers() {
        List<Offer> ignoredOffersList = new ArrayList<Offer>();
        for(Long offerId : this.ignoredOffers) {
            ignoredOffersList.add(getOfferById(offerId));// TODO nett wäre eine
            // restSchnittstelle für
            // mehrere Ids.... / evtl. in
            // Liste ablegen...
        }
        return ignoredOffersList;
    }

    @Override
    public List<Offer> getAllOffers() {
        List<Offer> allOffers = this.bbService.retrieveAllOffers();
        for(Offer o : allOffers) {
            if (this.ignoredOffers.contains(o.getId()))
                allOffers.remove(o);
        }
        return allOffers;
    }

    @Override
    public Category getCategory(String categoryName) {
        Category cat = this.bbService.retrieveCategory(categoryName);
        cat.ignoreOffers(new ArrayList<Long>(this.ignoredOffers));
        return cat;
    }

    @Override
    public Offer getOfferById(Long id) {
        return this.bbService.retrieveOfferById(id);
    }

    @Override
    public List<String> getAllCategoryNames() {
        return this.bbService.retrieveAllCategoryNames();
    }

    @Override
    public Image getImageById(Long id) {
        return this.bbService.retrieveImageById(id);
    }

    @Override
    public Long createOffer(String category, String header, String description, String contact, File image)
                    throws OfferCreationFailedException {
        OfferBean offer = new OfferBean(header, description, contact, category);
        OfferCreationStatus status = this.bbService.postNewOffer(offer, image);
        if (status.isSuccessfull()) {
            this.ownOffersToDelKey.put(status.getOfferId(), status.getDeletionKey());// TODO
                                                                                     // Persistent
                                                                                     // ablegen
            return status.getOfferId();
        }
        else {
            throw new OfferCreationFailedException("Offer could not be created");
        }
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

}
