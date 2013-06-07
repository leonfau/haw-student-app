package de.minimum.hawapp.app.blackboard.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.google.common.collect.Lists;

import de.minimum.hawapp.app.blackboard.api.Offer;

public class CategoryBeanTest extends TestCase {

    public void testIgnore() {
        OfferBean o1 = new OfferBean("O1", "", "", "Angebote");
        o1.setId(1);
        OfferBean o2 = new OfferBean("O2", "", "", "Angebote");
        o2.setId(2);
        OfferBean o3 = new OfferBean("O3", "", "", "Angebote");
        o3.setId(3);
        OfferBean o4 = new OfferBean("O4", "", "", "Angebote");
        o4.setId(4);
        OfferBean[] offers = { o1, o2, o3, o4 };
        CategoryBean cat = new CategoryBean();
        cat.setName("Angebote");
        cat.setAllOffers(offers);
        List<Offer> allOffers = new ArrayList<Offer>(cat.getAllOffers());
        Assert.assertEquals("Anzahl Elemente vollständig", offers.length, allOffers.size());
        Assert.assertTrue("Alle Elemente vorhanden", allOffers.containsAll(Arrays.asList(offers)));
        List<Long> toIgnore = Lists.newArrayList(o2.getId(), o4.getId());
        cat.ignoreOffers(toIgnore);

        List<Offer> allOffersAfterIgnore = cat.getAllOffers();
        Assert.assertEquals("Anzahl Elemente stimmt nach Ignore", allOffers.size() - toIgnore.size(),
                        allOffersAfterIgnore.size());
        Assert.assertTrue("o1 noch vorhanden", allOffersAfterIgnore.contains(o1));
        Assert.assertTrue("o3 noch vorhanden", allOffersAfterIgnore.contains(o3));
        Assert.assertFalse("o2 nicht mehr vorhanden", allOffersAfterIgnore.contains(o2));
        Assert.assertFalse("o4 nicht mehr vorhanden", allOffersAfterIgnore.contains(o4));

        cat.ignoreOffers(Arrays.asList(o2.getId()));
        List<Offer> allOffersAfterSenselessIgnore = cat.getAllOffers();
        Assert.assertEquals("Anzahl Elemente stimmt auch nach sinnlosem Ignore", allOffers.size() - toIgnore.size(),
                        allOffersAfterSenselessIgnore.size());
        Assert.assertTrue("o1 immernoch vorhanden", allOffersAfterSenselessIgnore.contains(o1));
        Assert.assertTrue("o3 immernoch vorhanden", allOffersAfterSenselessIgnore.contains(o3));
        Assert.assertFalse("o2 immernoch nicht vorhanden", allOffersAfterSenselessIgnore.contains(o2));
        Assert.assertFalse("o4 immernoch nicht vorhanden", allOffersAfterSenselessIgnore.contains(o4));

        cat.unignoreOffer(o1.getId());
        List<Offer> allOffersAfterSenselessUnignore = cat.getAllOffers();
        Assert.assertEquals("Anzahl Elemente stimmt auch nach sinnlosem Unignore", allOffers.size() - toIgnore.size(),
                        allOffersAfterSenselessUnignore.size());
        Assert.assertTrue("o1 immernoch vorhanden", allOffersAfterSenselessUnignore.contains(o1));
        Assert.assertTrue("o3 immernoch vorhanden", allOffersAfterSenselessUnignore.contains(o3));
        Assert.assertFalse("o2 immernoch nicht vorhanden", allOffersAfterSenselessUnignore.contains(o2));
        Assert.assertFalse("o4 immernoch nicht vorhanden", allOffersAfterSenselessUnignore.contains(o4));

        cat.unignoreOffer(o2.getId());
        List<Offer> allOffersAfterUnignore = cat.getAllOffers();
        Assert.assertEquals("Anzahl Elemente stimmt auch nach Unignore", allOffers.size() - toIgnore.size() + 1,
                        allOffersAfterUnignore.size());
        Assert.assertTrue("o1 immernoch vorhanden", allOffersAfterUnignore.contains(o1));
        Assert.assertTrue("o3 immernoch vorhanden", allOffersAfterUnignore.contains(o3));
        Assert.assertTrue("o2 wieder vorhanden", allOffersAfterUnignore.contains(o2));
        Assert.assertFalse("o4 immernoch nicht vorhanden", allOffersAfterUnignore.contains(o4));

    }
}
