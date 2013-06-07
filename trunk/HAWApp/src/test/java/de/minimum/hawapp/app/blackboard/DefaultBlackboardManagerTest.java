package de.minimum.hawapp.app.blackboard;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import android.content.Context;
import android.test.AndroidTestCase;
import de.minimum.hawapp.app.blackboard.api.BlackboardManager;
import de.minimum.hawapp.app.blackboard.api.Category;
import de.minimum.hawapp.app.blackboard.api.Offer;
import de.minimum.hawapp.app.blackboard.exceptions.OfferCreationFailedException;

public class DefaultBlackboardManagerTest extends TestCase {

    private Context context = new AndroidTestCase().getContext();
    private BlackboardManager bbManager = new DefaultBlackboardManager();

    public void testIgnore() {
        List<Offer> offers = this.bbManager.getAllOffers(this.context);
        List<Offer> ignored = this.bbManager.getIgnoredOffers(this.context);
        Offer toIgnore = getOfferNotInIgnore(offers, ignored);
        if (toIgnore == null)
            return;
        Category cat = this.bbManager.getCategory(this.context, toIgnore.getCategoryName());
        Assert.assertTrue("Offer ist in Cat", cat.getAllOffers().contains(toIgnore));

        this.bbManager.unignoreOffer(this.context, toIgnore);
        List<Offer> offersAfterSenselessUnignore = this.bbManager.getAllOffers(this.context);
        Category catAfterSenselessUnignore = this.bbManager.getCategory(this.context, toIgnore.getCategoryName());
        Assert.assertEquals("Liste größe stimmt", offers.size(), offersAfterSenselessUnignore.size());
        Assert.assertEquals("Cat größe stimmt.", cat.getAllOffers().size(), catAfterSenselessUnignore.getAllOffers()
                        .size());
        Assert.assertTrue("Offer ist immernoch in Liste", offersAfterSenselessUnignore.contains(toIgnore));
        Assert.assertTrue("Offer ist immernoch in Cat", catAfterSenselessUnignore.getAllOffers().contains(toIgnore));

        this.bbManager.ignoreOffer(this.context, toIgnore);
        List<Offer> offersAfterIgnore = this.bbManager.getAllOffers(this.context);
        Category catAfterIgnore = this.bbManager.getCategory(this.context, toIgnore.getCategoryName());
        Assert.assertEquals("Liste größe stimmt", offers.size() - 1, offersAfterIgnore.size());
        Assert.assertEquals("Cat größe stimmt.", cat.getAllOffers().size() - 1, catAfterIgnore.getAllOffers().size());
        Assert.assertFalse("Offer ist nicht mehr in Liste", offersAfterIgnore.contains(toIgnore));
        Assert.assertFalse("Offer ist nicht mehr in Cat", catAfterIgnore.getAllOffers().contains(toIgnore));

        this.bbManager.ignoreOffer(this.context, toIgnore);
        List<Offer> offersAfterSenselessIgnore = this.bbManager.getAllOffers(this.context);
        Category catAfterSenselessIgnore = this.bbManager.getCategory(this.context, toIgnore.getCategoryName());
        Assert.assertEquals("Liste größe stimmt", offers.size() - 1, offersAfterSenselessIgnore.size());
        Assert.assertEquals("Cat größe stimmt.", cat.getAllOffers().size() - 1, catAfterSenselessIgnore.getAllOffers()
                        .size());
        Assert.assertFalse("Offer ist immernoch nicht mehr in Liste", offersAfterSenselessIgnore.contains(toIgnore));
        Assert.assertFalse("Offer ist immernoch nicht mehr in Cat",
                        catAfterSenselessIgnore.getAllOffers().contains(toIgnore));

        this.bbManager.unignoreOffer(this.context, toIgnore);
        List<Offer> offersAfterUnignore = this.bbManager.getAllOffers(this.context);
        Category catAfterUnignore = this.bbManager.getCategory(this.context, toIgnore.getCategoryName());
        Assert.assertEquals("Liste größe stimmt", offers.size(), offersAfterUnignore.size());
        Assert.assertEquals("Cat größe stimmt.", cat.getAllOffers().size(), catAfterUnignore.getAllOffers().size());
        Assert.assertTrue("Offer wieder in Liste", offersAfterUnignore.contains(toIgnore));
        Assert.assertTrue("Offer wieder in Cat", catAfterUnignore.getAllOffers().contains(toIgnore));
    }

    private Offer getOfferNotInIgnore(List<Offer> from, List<Offer> ignored) {
        for(Offer o : from) {
            if (ignored.contains(o))
                return o;
        }
        return null;
    }

    public void testCreate() throws OfferCreationFailedException {
        boolean exceptionThrown = false;
        try {
            this.bbManager.createOffer(this.context, "svbbvhs", "Fail", "Bla", "blubb", null);
        }
        catch(OfferCreationFailedException e) {
            exceptionThrown = true;
        }
        Assert.assertTrue("Offer konnte nicht erstellt werden.", exceptionThrown);

        List<String> catNames = this.bbManager.getAllCategoryNames(this.context);
        String category = catNames.get(0);
        String header = "Testüberschrift";
        String description = "Ich verkaufe etwas ganz tolles";
        String contact = "Such mich";
        Long id = this.bbManager.createOffer(this.context, category, header, description, contact, null);

        Offer o = this.bbManager.getOfferById(this.context, id);
        Assert.assertNotNull("Offer existiert", o);
        Assert.assertTrue("Offer in Category vorhanden.", this.bbManager.getCategory(this.context, category)
                        .getAllOffers().contains(o));
        Assert.assertTrue("Offer in Eigenen vorhanden.", this.bbManager.getAllOwnOffers(this.context).contains(o));
        Assert.assertTrue("Offer in allen Offers vorhanden.", this.bbManager.getAllOffers(this.context).contains(o));
        Assert.assertEquals("Id richtig", (long)id, o.getId());
        Assert.assertEquals("Category richtig", category, o.getCategoryName());
        Assert.assertEquals("Header richtig", header, o.getHeader());
        Assert.assertEquals("Description richtig", description, o.getDescription());
        Assert.assertEquals("Contact richtig", contact, o.getContact());
        Assert.assertNotNull("Datum gesetzt", o.getDateOfCreation());
        Assert.assertNull("Image ist null", o.getImageId());

        Assert.assertTrue("Entfernen gelingt", this.bbManager.removeOwnOffer(this.context, o));
        Assert.assertFalse("Offer nicht mehr in Category vorhanden.", this.bbManager
                        .getCategory(this.context, category).getAllOffers().contains(o));
        Assert.assertFalse("Offer nicht mehr in Eigenen vorhanden.", this.bbManager.getAllOwnOffers(this.context)
                        .contains(o));
        Assert.assertFalse("Offer nicht mehr in allen Offers vorhanden.", this.bbManager.getAllOffers(this.context)
                        .contains(o));
        Assert.assertNull("Offer nicht mehr holbar.", this.bbManager.getOfferById(this.context, id));

        Assert.assertFalse("Entfernen gelingt nicht mehr", this.bbManager.removeOwnOffer(this.context, o));
    }
}
