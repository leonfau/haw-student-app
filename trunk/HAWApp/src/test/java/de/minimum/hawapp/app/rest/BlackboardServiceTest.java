package de.minimum.hawapp.app.rest;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.blackboard.api.OfferCreationStatus;
import de.minimum.hawapp.app.blackboard.beans.OfferBean;
import de.minimum.hawapp.app.rest.exceptions.RestServiceException;

public class BlackboardServiceTest extends TestCase {
    private BlackboardService bbService = new BlackboardService();

    public void testCreationAndDeletion() throws RestServiceException {
        OfferBean o = new OfferBean();
        List<String> allCategoryNames = this.bbService.retrieveAllCategoryNames();
        o.setCategoryName(allCategoryNames.get(0));
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

    public void testRetrieveAllOffers() throws RestServiceException {
        Assert.assertNotNull("Es existiert eine Liste", this.bbService.retrieveAllOffers());
    }

    public void testNullResults() throws RestServiceException {
        Assert.assertNull("Null result without Exception.", this.bbService.retrieveCategory("cjsddbcdgbdcxgNILN"));
    }

    public void testReportOffer() throws RestServiceException {
        this.bbService.reportOffer(this.bbService.retrieveAllOffers().get(0), "Test");
    }
}
