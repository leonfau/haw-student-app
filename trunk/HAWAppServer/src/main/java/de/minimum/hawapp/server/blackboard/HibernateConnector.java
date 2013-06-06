package de.minimum.hawapp.server.blackboard;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.api.PersistenceConnector;
import de.minimum.hawapp.server.blackboard.api.Persistent;
import de.minimum.hawapp.server.blackboard.api.Report;
import de.minimum.hawapp.server.blackboard.exceptions.PersistenceException;
import de.minimum.hawapp.server.blackboard.util.BlackboardFactoryManager;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.blackboard.CategoryEntity;
import de.minimum.hawapp.server.persistence.blackboard.ImageEntity;
import de.minimum.hawapp.server.persistence.blackboard.OfferEntity;
import de.minimum.hawapp.server.persistence.blackboard.ReportEntity;

//TODO: delete return anpassen
public class HibernateConnector implements PersistenceConnector {

    private Logger logger = LoggerFactory.getLogger(HibernateConnector.class);

    private HibernateSessionMgr hibernateSessionMgr = ManagerFactory.getManager(HibernateSessionMgr.class);

    @Override
    public Image persistImage(byte[] image) throws PersistenceException {
        Image img = BlackboardFactoryManager.newImage(image);
        return persistImage(img);
    }

    @Override
    public Image persistImage(Image image) throws PersistenceException {
        if (image instanceof ImageEntity) {
            persist(image);
            return image;
        }
        else {
            throw generatePersistenceException(image, ImageEntity.class);
        }
    }

    @Override
    public Offer persistOffer(Offer offer) throws PersistenceException {
        if (offer instanceof OfferEntity) {
            persist(offer);
            return offer;
        }
        else {
            throw generatePersistenceException(offer, OfferEntity.class);
        }
    }

    @Override
    public Category persistCategory(Category category) throws PersistenceException {
        if (category instanceof CategoryEntity) {
            persist(category);
            return category;
        }
        else {
            throw generatePersistenceException(category, CategoryEntity.class);
        }
    }

    @Override
    public Report persistReport(Report report) throws PersistenceException {
        if (report instanceof ReportEntity) {
            persist(report);
            return report;
        }
        else {
            throw generatePersistenceException(report, ReportEntity.class);
        }
    }

    @Override
    public boolean deleteOffer(Offer offer) throws PersistenceException {
        delete(offer);
        return true;
    }

    @Override
    public boolean deleteCategory(Category category) throws PersistenceException {
        delete(category);
        return true;
    }

    @Override
    public boolean deleteReport(Report report) throws PersistenceException {
        delete(report);
        return true;
    }

    @Override
    public boolean deleteImage(Image image) throws PersistenceException {
        delete(image);
        return true;
    }

    @Override
    public boolean deleteOffer(long offerId) throws PersistenceException {
        deleteById("OfferEntity", "id", offerId);
        return true;
    }

    @Override
    public boolean deleteCategory(String categoryName) throws PersistenceException {
        deleteById("CategoryEntity", "name", categoryName);
        return true;
    }

    @Override
    public boolean deleteReport(long reportId) throws PersistenceException {
        deleteById("ReportEntity", "id", reportId);
        return true;
    }

    @Override
    public boolean deleteImage(long imageId) throws PersistenceException {
        deleteById("ImageEntity", "id", imageId);
        return true;
    }

    @Override
    public Category updateCategory(Category category) throws PersistenceException {
        if (category instanceof CategoryEntity) {
            update(category);
            return category;
        }
        else {
            throw generatePersistenceException(category, CategoryEntity.class);
        }
    }

    @Override
    public Image loadImage(long id) throws PersistenceException {
        return loadPersistent(ImageEntity.class, id);
    }

    @Override
    public Offer loadOffer(long id) throws PersistenceException {
        return loadPersistent(OfferEntity.class, id);
    }

    @Override
    public Report loadReport(long id) throws PersistenceException {
        return loadPersistent(ReportEntity.class, id);
    }

    @Override
    public Category loadCategory(String name) throws PersistenceException {
        return loadPersistent(CategoryEntity.class, name);
    }

    @Override
    public List<Category> loadAllCategories() throws PersistenceException {
        return loadTable(CategoryEntity.class);
    }

    @Override
    public List<Offer> loadAllOffers() throws PersistenceException {
        return loadTable(OfferEntity.class);
    }

    @Override
    public boolean removeOldOffers(int ageInDays) throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DAY_OF_MONTH, -ageInDays);
            session.createQuery("delete from OfferEntity where dateOfCreation < :date").setDate("date", cal.getTime())
                            .executeUpdate();
            trans.commit();
            return true;
        }
        catch(HibernateException ex) {
            this.logger.error("Deletion of offers older than " + ageInDays + " was not possible: ", ex);
            throw new PersistenceException(ex);
        }
    }

    private <T extends Persistent<T>> List<T> loadTable(final Class<?> clazz) throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            @SuppressWarnings("unchecked")
            List<T> obj = session.createCriteria(clazz).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            trans.commit();
            return obj;
        }
        catch(HibernateException ex) {
            this.logger.error("Loading of Table of " + clazz + " was not possible: ", ex);
            throw new PersistenceException(ex);
        }
    }

    private <T extends Persistent<T>> T loadPersistent(final Class<?> clazz, final Serializable id)
                    throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            @SuppressWarnings("unchecked")
            T obj = (T)session.get(clazz, id);
            trans.commit();
            return obj;
        }
        catch(HibernateException ex) {
            this.logger.error("Loading of " + clazz + " with id: " + id + " was not possible: ", ex);
            throw new PersistenceException(ex);
        }
    }

    private void persist(Persistent<?> pers) throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            session.persist(pers);
            trans.commit();
        }
        catch(HibernateException ex) {
            this.logger.error("Persistence of " + pers + " with was not possible: ", ex);
            throw new PersistenceException(ex);
        }
    }

    private void delete(Persistent<?> pers) throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            session.delete(pers);
            trans.commit();
        }
        catch(HibernateException ex) {
            this.logger.error("Deletion of " + pers + " was not possible: ", ex);
            throw new PersistenceException(ex);
        }
    }

    private void update(Persistent<?> pers) throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            session.update(pers);
            trans.commit();
        }
        catch(HibernateException ex) {
            this.logger.error("Update of " + pers + " was not possible: ", ex);
            throw new PersistenceException(ex);
        }
    }

    private void deleteById(String table, String idName, Serializable id) throws PersistenceException {
        try {
            Session session = this.hibernateSessionMgr.getCurrentSession();
            Transaction trans = session.getTransaction();
            trans.begin();
            session.createQuery("delete from " + table + " where " + idName + "= :id").setString("id", id.toString())
                            .executeUpdate();
            trans.commit();
        }
        catch(HibernateException ex) {
            this.logger.error("Deletion of " + idName + " from " + table + " with id: " + id + " was not possible: ",
                            ex);
            throw new PersistenceException(ex);
        }
    }

    private PersistenceException generatePersistenceException(Object isObj, Class<?> should) {
        String msg = "The class " + isObj.getClass() + " of " + isObj
                        + " is not usable to persist an Image in this Hibernate-Context. " + should + " is needed.";
        PersistenceException ex = new PersistenceException(msg);
        this.logger.error(msg, ex);
        return ex;
    }
}
