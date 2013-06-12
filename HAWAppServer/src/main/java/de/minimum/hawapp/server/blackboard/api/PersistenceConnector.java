package de.minimum.hawapp.server.blackboard.api;

import java.util.List;

import de.minimum.hawapp.server.blackboard.exceptions.PersistenceException;

public interface PersistenceConnector {

    Image persistImage(byte[] image) throws PersistenceException;

    Image persistImage(Image image) throws PersistenceException;

    Offer persistOffer(Offer offer) throws PersistenceException;

    Category persistCategory(Category category) throws PersistenceException;

    Report persistReport(Report report) throws PersistenceException;

    boolean deleteOffer(Offer offer) throws PersistenceException;

    boolean deleteCategory(Category category) throws PersistenceException;

    boolean deleteReport(Report report) throws PersistenceException;

    boolean deleteImage(Image image) throws PersistenceException;

    boolean deleteOffer(long offerId) throws PersistenceException;

    boolean deleteCategory(String categoryName) throws PersistenceException;

    boolean deleteReport(long reportId) throws PersistenceException;

    boolean deleteImage(long imageId) throws PersistenceException;

    Category updateCategory(Category category) throws PersistenceException;

    Image loadImage(long id) throws PersistenceException;

    Offer loadOffer(long id) throws PersistenceException;

    Report loadReport(long id) throws PersistenceException;

    Category loadCategory(String name) throws PersistenceException;

    List<Offer> loadOffersBySearchString(String searchStr) throws PersistenceException;

    List<Category> loadAllCategories() throws PersistenceException;

    List<Offer> loadAllOffers() throws PersistenceException;

    boolean removeOldOffers(int ageInDays) throws PersistenceException;
}
