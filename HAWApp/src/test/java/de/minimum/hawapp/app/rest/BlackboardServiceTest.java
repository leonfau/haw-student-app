package de.minimum.hawapp.app.rest;

import junit.framework.Assert;
import junit.framework.TestCase;
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.blackboard.api.OfferCreationStatus;
import de.minimum.hawapp.app.blackboard.beans.OfferBean;

public class BlackboardServiceTest extends TestCase {
    private BlackboardService bbService = new BlackboardService();

    public void testCreationAndDeletion() {
        OfferBean o = new OfferBean();
        o.setCategoryName(this.bbService.retrieveAllCategoryNames().get(0));
        o.setHeader("TestOffer");
        o.setDescription("Offer Description");
        o.setContact("TestContact");
        OfferCreationStatus status = this.bbService.postNewOffer(o, null);
        Assert.assertTrue("Erstellung erfolgreich.", status.isSuccessfull());
        Offer o2 = this.bbService.retrieveOfferById(status.getOfferId());
        Assert.assertEquals("Id korrekt", o.getId(), o2.getId());
        Assert.assertNotNull("Date gesetzt", o.getDateOfCreation());
        Assert.assertEquals("Kategorie korrekt", o.getCategoryName(), o2.getCategoryName());
        Assert.assertEquals("Header korrekt", o.getHeader(), o2.getHeader());
        Assert.assertEquals("Description korrekt", o.getDescription(), o2.getDescription());
        Assert.assertEquals("Kontakt korrekt", o.getContact(), o2.getContact());

        Assert.assertFalse("Löschen misslingt", this.bbService.removeOffer(o2, status.getDeletionKey() + "ABC"));
        Assert.assertTrue("Es wird gelöscht", this.bbService.removeOffer(o2, status.getDeletionKey()));

        Assert.assertNull("Angebot ist weg", this.bbService.retrieveOfferById(o2.getId()));
    }
}
