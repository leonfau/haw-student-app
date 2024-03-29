package de.minimum.hawapp.server.facade.serviceprovider;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import de.minimum.hawapp.server.persistence.blackboard.ImageEntity;
import de.minimum.hawapp.test.rest.entities.blackboardservice.CategoryEntity;
import de.minimum.hawapp.test.rest.entities.blackboardservice.OfferCreationStatusEntity;
import de.minimum.hawapp.test.rest.entities.blackboardservice.OfferEntity;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;

public class BlackboardServiceTest extends RestTest {

    private static final String IMG_PATH = "." + File.separator + "src" + File.separator + "test" + File.separator
                    + "resources" + File.separator + "images" + File.separator + "hamburg-Elbe-Nacht.jpg";
    private static final String BLACKBOARD_SERVICE_URL = RestTest.REST_SERVICE_ADDRESS + "/blackboardservice";

    @Rule
    public AutoCleanUpRule cleanUpRule = new AutoCleanUpRule();

    @Test
    public void reportTest() {
        OfferCreationStatusEntity offer = createOffer(getAllCategories().get(0), "Böse", "Ich erzähle böse Sachen",
                        "Idiot", null);
        String reason = "Er hat sich über mich lustig gemacht :,-(";
        long offerId = offer.getOfferId();
        WebResource webResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL + "/report");
        Form form = new Form();
        form.add("offerId", offerId);
        form.add("reason", reason);
        ClientResponse response = webResource.post(ClientResponse.class, form);
        Assert.assertEquals("Server sagt ok", 200, response.getStatus());
    }

    @Test
    public void creationAndDeletionTest() {
        String header = "Fish and chips";
        String description = "Englischer genuss!!! Noch Fragen?";
        String contact = "London in der Telefonzelle :-P";
        List<String> categoryNames = getAllCategories();
        Assert.assertTrue("Namen vorhanden", categoryNames.size() > 0);

        String categoryName = categoryNames.get(0);
        File img = new File(BlackboardServiceTest.IMG_PATH);
        Date compDate = new Date();// Funktioniert nur weil Test und Server auf
                                   // der selben Maschine laufen
        OfferCreationStatusEntity status = createOffer(categoryName, header, description, contact, img);
        Assert.assertTrue("Erfolgreich erzeugt", status.isSuccessfull());

        OfferEntity offer = getOffer(status.getOfferId());
        validOfferOnServerTest(offer, status.getOfferId(), categoryName, header, description, contact, img, compDate);

        CategoryEntity category = getCategory(offer.getCategoryName());
        Assert.assertTrue("Angebot in Kategorie enthalten", category.getAllOffers().contains(offer));

        boolean removedFailed = removeOffer(offer, status.getDeletionKey() + "ABC");
        OfferEntity offer2 = getOffer(status.getOfferId());
        validOfferOnServerTest(offer2, status.getOfferId(), categoryName, header, description, contact, img, compDate);// Ist
                                                                                                                       // auch
                                                                                                                       // noch
                                                                                                                       // alles
                                                                                                                       // vorhanden
        CategoryEntity category2 = getCategory(offer.getCategoryName());
        Assert.assertTrue("Angebot immernoch in Kategorie enthalten", category2.getAllOffers().contains(offer));
        Assert.assertTrue("Offer != null", offer2 != null);
        Assert.assertFalse("Nicht gelöscht", removedFailed);

        boolean removedSuccess = removeOffer(offer, status.getDeletionKey());
        WebResource getOfferResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL + "/offer/"
                        + offer.getId());
        ClientResponse getOfferResponse = getOfferResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        WebResource getImageResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL + "/image/"
                        + offer.getImageId());
        ClientResponse getImageResponse = getImageResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        CategoryEntity category3 = getCategory(offer.getCategoryName());
        Assert.assertTrue("Angebot gelöscht", removedSuccess);
        Assert.assertTrue("Angebot nicht mehr vorhanden", noContent(getOfferResponse));
        Assert.assertTrue("Bild nicht mehr vorhanden", noContent(getImageResponse));
        Assert.assertFalse("Angebot nicht mehr in Kategorie enthalten", category3.getAllOffers().contains(offer));

        OfferCreationStatusEntity status2 = createOffer(categoryName, header, null, null, null);
        Assert.assertTrue("Optionales Feld", status2.isSuccessfull());
        OfferEntity offer3 = getOffer(status2.getOfferId());
        validOfferOnServerTest(offer3, status2.getOfferId(), categoryName, header, null, null, null, compDate);

        OfferCreationStatusEntity status3 = createOffer(categoryName, null, null, null, null);
        Assert.assertFalse("Nicht optionales Feld", status3.isSuccessfull());
    }

    @Test
    public void allCategoriesUnique() {
        String header = "Fish and chips";
        String description = "Englischer genuss!!! Noch Fragen?";
        String contact = "London in der Telefonzelle :-P";
        List<String> categoryNames = getAllCategories();
        Assert.assertTrue("Namen vorhanden", categoryNames.size() > 0);

        String categoryName = categoryNames.get(0);

        createOffer(categoryName, header, description, contact, null);
        createOffer(categoryName, header, description, contact, null);
        createOffer(categoryName, header, description, contact, null);
        createOffer(categoryName, header, description, contact, null);
        createOffer(categoryName, header, description, contact, null);

        List<String> allCategoryNames = getAllCategories();
        Assert.assertEquals("Alle Namen Unique", new HashSet<String>(allCategoryNames).size(), allCategoryNames.size());
    }

    private CategoryEntity getCategory(String categoryName) {
        WebResource getCategoriesResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL
                        + "/category/" + categoryName);
        ClientResponse getCategoriesResponse = getCategoriesResource.accept(MediaType.APPLICATION_JSON).get(
                        ClientResponse.class);
        checkResponse(getCategoriesResponse);
        CategoryEntity cat = getCategoriesResponse.getEntity(CategoryEntity.class);
        return cat;
    }

    private boolean removeOffer(OfferEntity offer, String deletionKey) {
        WebResource getCategoriesResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL
                        + "/remove/offerid/" + offer.getId() + "/deletionkey/" + deletionKey);
        ClientResponse getCategoriesResponse = getCategoriesResource.accept(MediaType.APPLICATION_JSON).delete(
                        ClientResponse.class);
        return getCategoriesResponse.getStatus() == 200;
    }

    private boolean isSameDay(Date d1, Date d2) {
        Calendar d1Cal = Calendar.getInstance();
        d1Cal.setTime(d1);
        Calendar d2Cal = Calendar.getInstance();
        d2Cal.setTime(d2);
        return ((d1Cal.get(Calendar.DAY_OF_YEAR) == d2Cal.get(Calendar.DAY_OF_YEAR)) && (d1Cal.get(Calendar.YEAR) == d2Cal
                        .get(Calendar.YEAR)));
    }

    private void validOfferOnServerTest(OfferEntity offerToCompare, long offerId, String categoryName, String header,
                    String description, String contact, File img, Date compDate) {
        Assert.assertEquals("Id richtig", offerId, offerToCompare.getId());
        Assert.assertEquals("Kategorie richtig", categoryName, offerToCompare.getCategoryName());
        Assert.assertEquals("Überschrift richtig", header, offerToCompare.getHeader());
        Assert.assertEquals("Beschreibung richtig", description, offerToCompare.getDescription());
        Assert.assertEquals("Kontakt richtig", contact, offerToCompare.getContact());
        Assert.assertTrue("Datum != null", offerToCompare.getDateOfCreation() != null);
        Assert.assertTrue("DateOfCreation am selben Tag", isSameDay(compDate, offerToCompare.getDateOfCreation()));

        if (img != null) {
            byte[] imgBefore = null;
            try {
                imgBefore = IOUtils.toByteArray(img.toURI());
            }
            catch(IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ImageEntity imgEntity = getImage(offerToCompare.getImageId());
            byte[] imgAfter = imgEntity.getImage();
            Assert.assertEquals("Bild Id richtig", offerToCompare.getImageId(), imgEntity.getId());
            Assert.assertArrayEquals("Dasselbe Bild", imgBefore, imgAfter);
        }
    }

    private ImageEntity getImage(long imageId) {
        WebResource getCategoriesResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL
                        + "/image/" + imageId);
        ClientResponse getCategoriesResponse = getCategoriesResource.accept(MediaType.APPLICATION_JSON).get(
                        ClientResponse.class);
        checkResponse(getCategoriesResponse);
        ImageEntity image = getCategoriesResponse.getEntity(ImageEntity.class);
        return image;
    }

    private OfferEntity getOffer(long offerId) {
        WebResource getCategoriesResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL
                        + "/offer/" + offerId);
        ClientResponse getCategoriesResponse = getCategoriesResource.accept(MediaType.APPLICATION_JSON).get(
                        ClientResponse.class);
        checkResponse(getCategoriesResponse);
        OfferEntity offer = getCategoriesResponse.getEntity(OfferEntity.class);
        return offer;
    }

    private OfferCreationStatusEntity createOffer(String category, String header, String description, String contact,
                    File img) {
        WebResource createOfferResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL
                        + "/newoffer");
        FormDataMultiPart form = new FormDataMultiPart();
        addField(form, "category", category);
        addField(form, "header", header);
        addField(form, "description", description);
        addField(form, "contact", contact);
        if (img != null)
            form.bodyPart(new FileDataBodyPart("image", img, MediaType.MULTIPART_FORM_DATA_TYPE));
        ClientResponse response = createOfferResource.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class,
                        form);
        checkResponse(response);
        return response.getEntity(OfferCreationStatusEntity.class);
    }

    private void addField(FormDataMultiPart form, String key, Object value) {
        if (value != null)
            form.field(key, value.toString());
    }

    private List<String> getAllCategories() {
        WebResource getCategoriesResource = this.client.resource(BlackboardServiceTest.BLACKBOARD_SERVICE_URL
                        + "/allcategorynames");
        ClientResponse getCategoriesResponse = getCategoriesResource.accept(MediaType.APPLICATION_JSON).get(
                        ClientResponse.class);
        checkResponse(getCategoriesResponse);
        List<String> names = getCategoriesResponse.getEntity(new GenericType<List<String>>(List.class));
        return names;
    }

    private boolean noContent(ClientResponse response) {
        return response.getStatus() == 204;
    }
}
