package de.minimum.hawapp.server.blackboard;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minimum.hawapp.server.blackboard.api.BlackboardManager;
import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.api.PersistenceConnector;
import de.minimum.hawapp.server.blackboard.api.Report;
import de.minimum.hawapp.server.blackboard.exceptions.PersistenceException;
import de.minimum.hawapp.server.blackboard.util.BlackboardFactoryManager;
import de.minimum.hawapp.server.blackboard.util.OfferCreationStatus;

//TODO Cachinggröße -> Clockalgorithmus zum verwalten?
public class CachingBlackboardManager implements BlackboardManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlackboardManager.class);

    private static final OfferCreationStatus FAILED_CREATION = new OfferCreationStatus(-1, false, "", null);
    private static final int OFFER_MAX_AGE_IN_DAYS = 7;// TODO abklären
    private static final int DELETION_TIMERINTERVAL_IN_HOURS = 24;// TODO
                                                                  // abklären

    private Map<String, Category> categories = new HashMap<String, Category>();
    private Map<Long, Offer> offers = new HashMap<>();
    private Map<Long, Image> images = new HashMap<>();

    private PersistenceConnector persConnector = BlackboardFactoryManager.getPersistenceConnector();
    private Timer offerDeletionTimer;

    public CachingBlackboardManager() {
        try {
            List<Category> allCategories = this.persConnector.loadAllCategories();
            for(Category cat : allCategories) {
                this.categories.put(cat.getName(), cat);
            }
        }
        catch(PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        TimerTask deleteAction = new TimerTask() {
            @Override
            public void run() {
                try {
                    CachingBlackboardManager.this.persConnector
                                    .removeOldOffers(CachingBlackboardManager.OFFER_MAX_AGE_IN_DAYS);
                    removeOldOffers();
                }
                catch(PersistenceException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);
        this.offerDeletionTimer = new Timer();
        this.offerDeletionTimer.schedule(deleteAction, date.getTime(), TimeUnit.MILLISECONDS.convert(
                        CachingBlackboardManager.DELETION_TIMERINTERVAL_IN_HOURS, TimeUnit.HOURS));

    }

    private void removeOldOffers() {

    }

    @Override
    public OfferCreationStatus createOffer(String category, String header, String description, String contact,
                    byte[] image) {
        try {
            if (!checkRequirements(category, header))
                return CachingBlackboardManager.FAILED_CREATION;
            Category cat = getCategory(category);
            if (cat == null) {
                return CachingBlackboardManager.FAILED_CREATION;
            }
            Image img = BlackboardFactoryManager.newImage(image);
            if (image != null)
                img = this.persConnector.persistImage(image);
            this.images.put(img.getId(), img);
            Offer offer = BlackboardFactoryManager.newOffer(cat, header, description, contact, new Date(), img.getId());
            offer = this.persConnector.persistOffer(offer);
            this.offers.put(offer.getId(), offer);
            return new OfferCreationStatus(offer.getId(), true, offer.getDeletionKey(), offer.getDateOfCreation());
        }
        catch(PersistenceException ex) {
            // TODO was genau tun? nochmal Ausprobieren???
            return CachingBlackboardManager.FAILED_CREATION;
        }
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
        this.images.remove(offer.getImageId());
        this.offers.remove(offer.getId());
        try {
            this.persConnector.deleteOffer(offer);// TODO evtl. archivieren?
            this.persConnector.deleteImage(offer.getImageId());
        }
        catch(PersistenceException ex) {
            // TODO
            return false;
        }
        return true;
    }

    private boolean checkDeletionKey(Offer toDelete, String deletionKey) {
        return toDelete.getDeletionKey().equals(deletionKey);
    }

    @Override
    public void reportOffer(long offerId, String reason) {
        try {
            CachingBlackboardManager.LOGGER.info("Reported Offer: " + offerId + "; reason: " + reason);
            Report report = BlackboardFactoryManager.newReport(getOffer(offerId), reason);
            this.persConnector.persistReport(report);
        }
        catch(PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public List<Offer> getAllOffers() {
        try {
            return this.persConnector.loadAllOffers();// TODO ist Caching
                                                      // möglich?
            // TODO Kategorien anpassen?
        }
        catch(PersistenceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Offer getOffer(long offerId) {
        Offer offer = this.offers.get(offerId);
        if (offer == null)
            try {
                offer = this.persConnector.loadOffer(offerId);
            }
            catch(PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return offer;
    }

    @Override
    public Category getCategory(String category) {
        Category cat = this.categories.get(category);
        if (cat == null)
            try {
                return this.persConnector.loadCategory(category);
            }
            catch(PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        else
            return cat;
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
        return new ArrayList<>(this.categories.values());// TODO auch in
                                                         // Datenbank
                                                         // nachschauen
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
