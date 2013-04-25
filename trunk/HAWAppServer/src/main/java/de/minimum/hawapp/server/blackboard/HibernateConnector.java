package de.minimum.hawapp.server.blackboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.api.PersistenceConnector;
import de.minimum.hawapp.server.blackboard.api.Report;
import de.minimum.hawapp.server.blackboard.exceptions.PersistenceException;
import de.minimum.hawapp.server.blackboard.util.BlackboardFactoryManager;
import de.minimum.hawapp.server.persistence.blackboard.ImageEntity;
import de.minimum.hawapp.server.persistence.blackboard.OfferEntity;

public class HibernateConnector implements PersistenceConnector {

    private Logger logger = LoggerFactory.getLogger(HibernateConnector.class);

    private volatile static long offerId = 0;// TODO entfernen sobald DB
                                             // vorhanden ist

    private volatile static long imageId = 0;// TODO entfernen sobald DB
                                             // vorhanden ist

    @Override
    public Image persistImage(byte[] image) throws PersistenceException {
        Image img = BlackboardFactoryManager.newImage(image);
        return persistImage(img);
    }

    @Override
    public Image persistImage(Image image) throws PersistenceException {
        if (image instanceof ImageEntity) {
            ((ImageEntity)image).setId(HibernateConnector.imageId);// TODO mit
                                                                   // Datenbank
                                                                   // arbeiten
            HibernateConnector.imageId++;
            return image;
        }
        else {
            String msg = "The class " + image.getClass() + " of " + image
                            + " is not usable to persist an Image in this Hibernate-Context. " + ImageEntity.class
                            + " is needed.";
            PersistenceException ex = new PersistenceException(msg);
            this.logger.error(msg, ex);
            throw ex;
        }
    }

    @Override
    public Offer persistOffer(Offer offer) throws PersistenceException {
        if (offer instanceof OfferEntity) {
            ((OfferEntity)offer).setId(HibernateConnector.offerId);// TODO mit
                                                                   // Datenbank
                                                                   // arbeiten
            HibernateConnector.offerId++;
            return offer;
        }
        else {
            String msg = "The class " + offer.getClass() + " of " + offer
                            + " is not usable to persist an Offer in this Hibernate-Context. " + OfferEntity.class
                            + " is needed.";
            PersistenceException ex = new PersistenceException(msg);
            this.logger.error(msg, ex);
            throw ex;
        }
    }

    @Override
    public Category persistCategory(Category category) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Report persistReport(Report report) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteOffer(Offer offer) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteCategory(Category category) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteReport(Report report) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteImage(Image image) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteOffer(long offerId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteCategory(long categoryId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteReport(long reportId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteImage(long imageId) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Category updateCategory(Category category) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }
}
