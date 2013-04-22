package de.minimum.hawapp.server.blackboard.api;

import de.minimum.hawapp.server.blackboard.util.PersistenceResult;

public interface PersistenceConnector {

    PersistenceResult<Image> persistImage(byte[] image);

    PersistenceResult<Offer> persistOffer(Offer offer, String deletionKey);

    PersistenceResult<Category> persistCategory(Category category);

    PersistenceResult<Report> persistReport(Report report);

    boolean deleteOffer(Offer offer);

    boolean deleteCategory(Category category);

    boolean deleteReport(Report report);

    boolean deleteImage(Image image);

    boolean deleteOffer(long offerId);

    boolean deleteCategory(long categoryId);

    boolean deleteReport(long reportId);

    boolean deleteImage(long imageId);

    PersistenceResult<Offer> updateOffer(Offer offer);

    PersistenceResult<Category> updateCategory(Category category);

    PersistenceResult<Report> updateReport(Report report);

    PersistenceResult<Image> updateImage(Image image);
}
