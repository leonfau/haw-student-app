package de.minimum.hawapp.server.blackboard.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.minimum.hawapp.server.blackboard.HibernateConnector;
import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.api.PersistenceConnector;
import de.minimum.hawapp.server.blackboard.api.Report;
import de.minimum.hawapp.server.persistence.blackboard.CategoryEntity;
import de.minimum.hawapp.server.persistence.blackboard.ImageEntity;
import de.minimum.hawapp.server.persistence.blackboard.OfferEntity;
import de.minimum.hawapp.server.persistence.blackboard.ReportEntity;

public class BlackboardFactoryManager {

    private static final PersistenceConnector persistenceCon = new HibernateConnector();
    private static final int KEY_LENGTH = 11;
    private static final KeyGenerator keyGen = new FullyRandomizedUrlValidGenerator();

    private BlackboardFactoryManager() {
    }

    public static PersistenceConnector getPersistenceConnector() {
        return BlackboardFactoryManager.persistenceCon;
    }

    public static Offer newOffer(Category category, String header, String description, String contact, double price,
                    Date dateOfCreation, long imageId) {
        OfferEntity offer = new OfferEntity();
        offer.setCategory(category);
        offer.setHeader(header);
        offer.setDescription(description);
        offer.setContact(contact);
        offer.setPrice(price);
        offer.setDateOfCreation(dateOfCreation);
        offer.setImageId(imageId);
        offer.setDeletionKey(BlackboardFactoryManager.generateDeletionKey(offer));
        return offer;
    }

    private static String generateDeletionKey(OfferEntity offer) {
        return BlackboardFactoryManager.keyGen.generate(offer, BlackboardFactoryManager.KEY_LENGTH);
    }

    public static Image newImage(byte[] image) {
        ImageEntity img = new ImageEntity();
        img.setImage(image);
        return img;
    }

    public static Category newCategory(String name, List<Offer> offers) {
        CategoryEntity cat = new CategoryEntity();
        cat.setName(name);
        cat.setAllOffers(offers);
        return cat;
    }

    public static Category newCategory(String name) {
        return BlackboardFactoryManager.newCategory(name, new ArrayList<Offer>());
    }

    public static Report newReport(Offer offer, String reason) {
        ReportEntity report = new ReportEntity();
        report.setOffer(offer);
        report.setReason(reason);
        return report;
    }
}
