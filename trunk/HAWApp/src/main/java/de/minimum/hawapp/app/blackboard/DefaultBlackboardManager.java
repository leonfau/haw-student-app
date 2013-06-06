package de.minimum.hawapp.app.blackboard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import de.minimum.hawapp.app.rest.exceptions.RestServiceException;

//TODO regelmäßig überprüfen ob auch schon sehr alte eigene noch im Speicher liegen die entfernt werden müssen
public class DefaultBlackboardManager implements BlackboardManager {
    private enum PersistentCollections {
        IGNORED, OWN
    }

    private static final String IGNORED_OFFERS_FILE = "ignoredOffers.pers";
    private static final String OWN_OFFERS_FILE = "ownOffers.pers";

    private BlackboardService bbService = new BlackboardService();

    private Map<Long, String> offerToDelKey = new HashMap<Long, String>();
    private Set<Long> ignoredOffers = new HashSet<Long>();
    private Context context = null;// Beim benutzen immer auf Null Checken!!!
    private boolean loaded = false;// Signalisiert ob Dateien schon vom
                                   // Filesystem geladen wurden

    public DefaultBlackboardManager() {
    }

    @Override
    public List<Offer> getAllOwnOffers(Context context) {
        return getAllOffersFromList(this.offerToDelKey.keySet(), PersistentCollections.OWN);
    }

    @Override
    public boolean removeOwnOffer(Context context, Offer offer) {
        String delKey = this.offerToDelKey.get(offer.getId());
        if (delKey == null)
            return false;
        boolean removed = false;
        try {
            removed = this.bbService.removeOffer(offer, delKey);
        }
        catch(RestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (removed) {
            this.offerToDelKey.remove(offer.getId());// TODO auch aus dem
                                                     // Speicher
            // löschen
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean ignoreOffer(Context context, Offer offer) {
        this.ignoredOffers.add(offer.getId());
        return true;
    }

    @Override
    public boolean unignoreOffer(Context context, Offer offer) {
        return this.ignoredOffers.remove(offer.getId());
    }

    @Override
    public List<Offer> getIgnoredOffers(Context context) {
        List<Long> ignoredOffersList = new ArrayList<Long>();
        for(Long offerId : this.ignoredOffers) {
            ignoredOffersList.add(offerId);
        }
        return getAllOffersFromList(ignoredOffersList, PersistentCollections.IGNORED);
    }

    @Override
    public List<Offer> getAllOffers(Context context) {
        try {
            List<Offer> allOffers = this.bbService.retrieveAllOffers();
            List<Offer> toRemove = new ArrayList<Offer>();
            for(Offer o : allOffers) {
                if (this.ignoredOffers.contains(o.getId()))
                    toRemove.add(o);
            }
            allOffers.removeAll(toRemove);
            return allOffers;
        }
        catch(RestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category getCategory(Context context, String categoryName) {
        try {
            Category cat = this.bbService.retrieveCategory(categoryName);
            List<Long> toIgnore = new ArrayList<Long>();
            for(Long offerId : this.ignoredOffers) {
                toIgnore.add(offerId);
            }
            cat.ignoreOffers(toIgnore);
            return cat;
        }
        catch(RestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Offer getOfferById(Context context, Long id) {
        try {
            return this.bbService.retrieveOfferById(id);
        }
        catch(RestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> getAllCategoryNames(Context context) {
        try {
            return this.bbService.retrieveAllCategoryNames();
        }
        catch(RestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Image getImageById(Context context, Long id) {
        try {
            return this.bbService.retrieveImageById(id);
        }
        catch(RestServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long createOffer(Context context, String category, String header, String description, String contact,
                    File image) throws OfferCreationFailedException {
        try {
            OfferBean offer = new OfferBean(header, description, contact, category);
            OfferCreationStatus status = this.bbService.postNewOffer(offer, image);
            if (status.isSuccessfull()) {
                this.offerToDelKey.put(status.getOfferId(), status.getDeletionKey());// TODO
                                                                                     // Persistent
                // ablegen
                return status.getOfferId();
            }
            else {
                throw new OfferCreationFailedException("Offer could not be created");
            }
        }
        catch(RestServiceException ex) {
            throw new OfferCreationFailedException("Problem with connection to Server.", ex);
        }
    }

    /**
     * Achtung: Entfernt nicht gefundene Angebote aus der übergenen Liste
     * 
     * @param offers
     * @return
     */
    private List<Offer> getAllOffersFromList(Collection<Long> offers, PersistentCollections toRemoveFrom) {
        List<Offer> ownOffersToReturn = new ArrayList<Offer>();
        List<Long> toRemove = new ArrayList<Long>();
        for(Long offerId : offers) {
            try {
                Offer o = this.bbService.retrieveOfferById(offerId);
                if (o == null)
                    toRemove.add(offerId);
                else
                    ownOffersToReturn.add(this.bbService.retrieveOfferById(offerId));
            }
            catch(RestServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }// TODO nett wäre eine
             // restSchnittstelle für
             // mehrere Ids.... / evtl. in
             // Liste ablegen...
        }
        for(Long offerId : toRemove) {
            if (toRemoveFrom == PersistentCollections.OWN) {
                this.offerToDelKey.remove(offerId);
            }
            else if (toRemoveFrom == PersistentCollections.IGNORED) {
                this.ignoredOffers.remove(offerId);
            }
        }
        return ownOffersToReturn;
    }
}
