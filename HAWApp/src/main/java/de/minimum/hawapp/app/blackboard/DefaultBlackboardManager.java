package de.minimum.hawapp.app.blackboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
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

    private Map<Long, String> offerToDelKey = null;
    private Set<Long> ignoredOffers = null;

    public DefaultBlackboardManager() {
    }

    @Override
    public List<Offer> getAllOwnOffers(Context context) {
        return getAllOffersFromList(context, this.getOrLoadOfferToDelKey(context).keySet(), PersistentCollections.OWN);
    }

    @Override
    public boolean removeOwnOffer(Context context, Offer offer) {
        Map<Long, String> ownOffersToDelKey = getOrLoadOfferToDelKey(context);
        String delKey = ownOffersToDelKey.get(offer.getId());
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
            ownOffersToDelKey.remove(offer.getId());
            persistOwnOffers(context);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean ignoreOffer(Context context, Offer offer) {
        Set<Long> loadedIgnoredOffers = getOrLoadIgnoredOffers(context);
        loadedIgnoredOffers.add(offer.getId());
        persistIgnoredOffers(context);
        return true;
    }

    @Override
    public boolean unignoreOffer(Context context, Offer offer) {
        Set<Long> loadedIgnoredOffers = getOrLoadIgnoredOffers(context);
        boolean successfull = loadedIgnoredOffers.remove(offer.getId());
        persistIgnoredOffers(context);
        return successfull;
    }

    @Override
    public List<Offer> getIgnoredOffers(Context context) {
        return getAllOffersFromList(context, getOrLoadIgnoredOffers(context), PersistentCollections.IGNORED);
    }

    @Override
    public List<Offer> getAllOffers(Context context) {
        try {
            List<Offer> allOffers = this.bbService.retrieveAllOffers();
            List<Offer> toRemove = new ArrayList<Offer>();
            Set<Long> loadedIgnoredOffers = getOrLoadIgnoredOffers(context);
            for(Offer o : allOffers) {
                if (loadedIgnoredOffers.contains(o.getId()))// TODO kann ich
                                                            // hier
                                                            // meine ignorierten
                                                            // evtl. auch
                                                            // überprüfen?
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
            List<Long> toIgnore = new ArrayList<Long>(getOrLoadIgnoredOffers(context));// TODO
                                                                                       // können
                                                                                       // hier
                                                                                       // die
                                                                                       // Inorierten
                                                                                       // evtl.
                                                                                       // überprüft
                                                                                       // werden
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
            Map<Long, String> ownOfferToDelKey = getOrLoadOfferToDelKey(context);
            OfferBean offer = new OfferBean(header, description, contact, category);
            OfferCreationStatus status = this.bbService.postNewOffer(offer, image);
            if (status.isSuccessfull()) {
                ownOfferToDelKey.put(status.getOfferId(), status.getDeletionKey());// TODO
                persistOwnOffers(context);
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
    private List<Offer> getAllOffersFromList(Context context, Collection<Long> offers,
                    PersistentCollections toRemoveFrom) {
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
        Map<Long, String> ownOffersToDelKey = null;
        Set<Long> loadedInoredOffers = null;
        if (toRemoveFrom == PersistentCollections.OWN) {
            ownOffersToDelKey = getOrLoadOfferToDelKey(context);
        }
        else if (toRemoveFrom == PersistentCollections.IGNORED) {
            loadedInoredOffers = getOrLoadIgnoredOffers(context);
        }
        for(Long offerId : toRemove) {
            if (ownOffersToDelKey != null) {
                ownOffersToDelKey.remove(offerId);
            }
            else if (loadedInoredOffers != null) {
                loadedInoredOffers.remove(offerId);
            }
        }
        if (ownOffersToDelKey != null) {
            persistOwnOffers(context);
        }
        else if (loadedInoredOffers != null) {
            persistIgnoredOffers(context);
        }
        return ownOffersToReturn;
    }

    @Override
    public List<Offer> searchOffers(Context context, String searchString) {
        // TODO Suchen
        return getAllOffers(context);
    }

    private Map<Long, String> getOrLoadOfferToDelKey(Context context) {
        if (this.offerToDelKey == null) {
            loadOwnOffersToDelKey(context);
        }
        return this.offerToDelKey;
    }

    private Set<Long> getOrLoadIgnoredOffers(Context context) {
        if (this.ignoredOffers == null) {
            loadIgnoredOffers(context);
        }
        return this.ignoredOffers;
    }

    private void persistOwnOffers(Context context) {
        try {
            persist(context, this.getOrLoadOfferToDelKey(context), DefaultBlackboardManager.OWN_OFFERS_FILE);
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadOwnOffersToDelKey(Context context) {
        try {
            this.offerToDelKey = load(context, DefaultBlackboardManager.OWN_OFFERS_FILE);
        }
        catch(FileNotFoundException e) {
            this.offerToDelKey = new HashMap<Long, String>();
        }
        catch(StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void persistIgnoredOffers(Context context) {
        try {
            persist(context, getOrLoadIgnoredOffers(context), DefaultBlackboardManager.IGNORED_OFFERS_FILE);
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void loadIgnoredOffers(Context context) {
        try {
            this.ignoredOffers = load(context, DefaultBlackboardManager.IGNORED_OFFERS_FILE);
        }
        catch(FileNotFoundException e) {
            this.ignoredOffers = new HashSet<Long>();
        }
        catch(StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void persist(Context context, Object toPersist, String filename) throws IOException {
        ObjectOutputStream objOut = null;
        FileOutputStream fOut = null;
        try {
            // Write to disk with FileOutputStream
            fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            // Write object with ObjectOutputStream
            objOut = new ObjectOutputStream(fOut);

            // Write object out to disk
            objOut.writeObject(toPersist);
            objOut.flush();
        }
        finally {
            if (objOut != null)
                objOut.close();
            if (fOut != null)
                fOut.close();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T load(Context context, String filename) throws StreamCorruptedException, IOException,
                    ClassNotFoundException {
        FileInputStream fIn = null;
        ObjectInputStream objIn = null;
        Object res = null;
        try {
            // Read from disk using FileInputStream
            fIn = context.openFileInput(filename);

            objIn = new ObjectInputStream(fIn);

            // Read an object

            res = objIn.readObject();

        }
        finally {
            if (objIn != null)
                objIn.close();
            if (fIn != null)
                fIn.close();
        }
        return (T)res;
    }

}
