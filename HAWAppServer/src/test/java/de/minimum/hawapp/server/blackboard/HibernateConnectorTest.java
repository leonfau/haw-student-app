package de.minimum.hawapp.server.blackboard;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.exceptions.PersistenceException;
import de.minimum.hawapp.server.persistence.blackboard.OfferEntity;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;

public class HibernateConnectorTest {
    @ClassRule
    public static AutoCleanUpRule rule = new AutoCleanUpRule();
    private HibernateConnector hibCon = new HibernateConnector();

    @Test
    public void test() throws PersistenceException {
        int days = 7;
        Category cat = this.hibCon.loadAllCategories().get(0);
        String header = "Header";
        String description = "descr";
        String contact = "contact";
        Calendar toStayCal = new GregorianCalendar();
        toStayCal.add(Calendar.DAY_OF_MONTH, -days);
        Date toStayDate = toStayCal.getTime();
        Calendar toRemoveCal = new GregorianCalendar();
        toRemoveCal.add(Calendar.DAY_OF_MONTH, -days - 1);
        Date toRemoveDate = toRemoveCal.getTime();
        Offer toStay = createOffer(cat, header, description, contact, toStayDate);
        Offer toRemove = createOffer(cat, header, description, contact, toRemoveDate);

        this.hibCon.persistOffer(toStay);
        this.hibCon.persistOffer(toRemove);
        Assert.assertEquals("toStay in DB", toStay, this.hibCon.loadOffer(toStay.getId()));
        Assert.assertEquals("toRemove in DB", toRemove, this.hibCon.loadOffer(toRemove.getId()));

        this.hibCon.removeOldOffers(days);
        Assert.assertEquals("toStay immernoch in DB", toStay, this.hibCon.loadOffer(toStay.getId()));
        Assert.assertEquals("toRemove nicht mehr in DB", null, this.hibCon.loadOffer(toRemove.getId()));

    }

    private Offer createOffer(Category cat, String header, String description, String contact, Date date) {
        OfferEntity offer = new OfferEntity();
        offer.setCategory(cat);
        offer.setHeader(header);
        offer.setDescription(description);
        offer.setContact(contact);
        offer.setDateOfCreation(date);
        offer.setDeletionKey("123");
        return offer;
    }
}
