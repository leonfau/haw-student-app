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
    public void removeOldOffersTest() throws PersistenceException {
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

    @Test
    public void searchTest() throws PersistenceException {
        Offer o = this.hibCon.loadAllOffers().get(0);
        String header = o.getHeader();
        String description = o.getDescription();
        String category = o.getCategoryName();
        String contact = o.getContact();

        Assert.assertTrue("Überschrift gefunden", this.hibCon.loadOffersBySearchString(header).contains(o));
        Assert.assertTrue("Beschreibung gefunden", this.hibCon.loadOffersBySearchString(description).contains(o));
        Assert.assertTrue("Kontakt gefunden", this.hibCon.loadOffersBySearchString(contact).contains(o));
        Assert.assertTrue("Kategorie gefunden", this.hibCon.loadOffersBySearchString(category).contains(o));

        if (header.length() >= 4)
            Assert.assertTrue("Teil von Überschrift gefunden",
                            this.hibCon.loadOffersBySearchString(header.substring(1, header.length() - 1)).contains(o));
        if (description.length() >= 4)
            Assert.assertTrue("Teil von Beschreibung gefunden",
                            this.hibCon.loadOffersBySearchString(description.substring(1, description.length() - 1))
                                            .contains(o));
        if (contact.length() >= 4)
            Assert.assertTrue("Teil von Kontakt gefunden",
                            this.hibCon.loadOffersBySearchString(contact.substring(1, contact.length() - 1))
                                            .contains(o));
        if (category.length() >= 4)
            Assert.assertTrue("Teil von Kategorie gefunden",
                            this.hibCon.loadOffersBySearchString(category.substring(1, category.length() - 1))
                                            .contains(o));

        Assert.assertFalse("Überschrift + \"ABC\" nicht gefunden", this.hibCon.loadOffersBySearchString(header + "ABC")
                        .contains(o));
        Assert.assertFalse("Beschreibung + \"ABC\" nicht gefunden",
                        this.hibCon.loadOffersBySearchString(description + "ABC").contains(o));
        Assert.assertFalse("Kontakt + \"ABC\" nicht gefunden", this.hibCon.loadOffersBySearchString(contact + "ABC")
                        .contains(o));
        Assert.assertFalse("Kategorie + \"ABC\" nicht gefunden", this.hibCon.loadOffersBySearchString(category + "ABC")
                        .contains(o));
    }
}
