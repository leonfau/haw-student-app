package de.minimum.hawapp.server.blackboard;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.api.PersistenceConnector;
import de.minimum.hawapp.server.blackboard.api.Report;
import de.minimum.hawapp.server.blackboard.util.PersistenceResult;

public class HibernateConnector implements PersistenceConnector {

    @Override
    public PersistenceResult<Image> persistImage(byte[] image) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersistenceResult<Offer> persistOffer(Offer offer, String deletionKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersistenceResult<Category> persistCategory(Category category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersistenceResult<Report> persistReport(Report report) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteCategory(Category category) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteReport(Report report) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteImage(Image image) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteOffer(long offerId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteCategory(long categoryId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteReport(long reportId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteImage(long imageId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PersistenceResult<Offer> updateOffer(Offer offer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersistenceResult<Category> updateCategory(Category category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersistenceResult<Report> updateReport(Report report) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PersistenceResult<Image> updateImage(Image image) {
        // TODO Auto-generated method stub
        return null;
    }
}
