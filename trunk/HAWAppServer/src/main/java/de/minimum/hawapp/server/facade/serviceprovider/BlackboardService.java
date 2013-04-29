package de.minimum.hawapp.server.facade.serviceprovider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.blackboard.api.BlackboardManager;
import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.util.OfferCreationStatus;
import de.minimum.hawapp.server.context.ManagerFactory;

@Singleton
@Path("/blackboardservice")
public class BlackboardService {

    private final BlackboardManager bbMngr = ManagerFactory.getManager(BlackboardManager.class);

    /**
     * Erstellt eine neues Angebot Sämtliche Daten müssen im Body übermittelt
     * werden
     * 
     * @param category
     *            Kategorie in dem das Angebot liegt -> diese muss schon auf dem
     *            Server vorliegen. Es wird keine neue Kategorie erzeugt
     * @param header
     *            Überschrift des Angebots
     * @param descr
     *            Beschreibung des Angebots -> darf leer sein
     * @param contact
     *            Kontaktdaten -> darf leer sein
     * @param uploadedImgStream
     *            Bild zu dem Angebot werden -> darf leer sein
     * @return Ein Statusobject mit Information dazu ob erfolgreich, dem
     *         deletionKey und dem erzeugtem Angebot
     */
    @POST
    @Path("newoffer")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public OfferCreationStatus newOffer(@FormDataParam("category") String category,
                    @FormDataParam("header") String header, @FormDataParam("description") String descr,
                    @FormDataParam("contact") String contact, @FormDataParam("image") InputStream uploadedImgStream) {
        // @FormDataParam("image") FormDataContentDisposition fileDetail -> zu
        // den Parametern hinzu fallse Deatils zu der Datei benötigt werden
        byte[] img = null;
        if (uploadedImgStream != null) {// TODO
            try {
                img = IOUtils.toByteArray(uploadedImgStream);
            }
            catch(IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return this.bbMngr.createOffer(category, header, descr, contact, img);
    }

    /**
     * Löscht das Angebiot mit der entsprechenden Id
     * 
     * @param offerId
     *            Id des Angebots zum löschen
     * @param delKey
     *            Schlüssel der einen Autorisiert das Angebot zu löschen ->
     *            diesen hat man beim Erstellen des Angebots erhalten
     * @return true wenn das Löschen erfolgreich war
     */
    @DELETE
    @Path("remove/offerid/{offerId}/deletionkey/{delKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean removeOffer(@PathParam("offerId") long offerId, @PathParam("delKey") String delKey) {
        return this.bbMngr.removeOffer(offerId, delKey);
    }

    /**
     * Meldet ein Angebot. Sämtliche Daten müssen im Body übermittelt werden
     * 
     * @param offerId
     *            Id des Angebots das gemeldet wird
     * @param reason
     *            Begründung warum das Angebot gemeldet wurde
     * @return
     */
    @POST
    @Path("report")
    public Response report(@FormParam("offerId") long offerId, @FormParam("reason") String reason) {
        this.bbMngr.reportOffer(offerId, reason);
        return Response.ok().build();
    }

    /**
     * Besorgt eine Liste mit allen vorliegenden Angeboten
     * 
     * @return
     */
    @GET
    @Path("alloffers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getAllOffers() {
        return this.bbMngr.getAllOffers();
    }

    /**
     * Besorgt das Angebot mit entsprechender Id
     * 
     * @param offerId
     *            Id des Angebots
     * @return
     */
    @GET
    @Path("offer/{offerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Offer getOffer(@PathParam("offerId") long offerId) {
        return this.bbMngr.getOffer(offerId);
    }

    /**
     * Basorgt eine Kategorie mit allen in ihr vorliegenden Angeboten
     * 
     * @param cat
     *            Name der zu besorgenden Kategorie
     * @return
     */
    @GET
    @Path("category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategory(@PathParam("category") String cat) {
        return this.bbMngr.getCategory(cat);
    }

    /**
     * Sucht nach Angeboten
     * 
     * @param searchStr
     *            Sucheingabe nach der in den Angeboten gesucht werden soll
     * @return Liste aller zutreffenden Angebote
     */
    @GET
    @Path("search/{searchStr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getOffersBySearchStr(@PathParam("searchStr") String searchStr) {
        // TODO URLDecoder verwenden um den SearchString wieder zu einem
        // regulären String zu machen
        return this.bbMngr.getOffersBySearchStr(searchStr);
    }

    /**
     * Sucht nach Angeboten
     * 
     * @param searchStr
     *            Sucheingabe nach der in den Angeboten gesucht werden soll
     * @param cat
     *            Kategorie in der nachgeschaut werden soll
     * @return Liste aller zutreffenden Angebote
     */
    @GET
    @Path("search/{searchStr}/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getOffersBySearchStrAndCategory(@PathParam("searchStr") String searchStr,
                    @PathParam("category") String cat) {
        // TODO URLDecoder verwenden um den SearchString wieder zu einem
        // regulären String zu machen
        return this.bbMngr.getOffersBySearchStrAndCategory(searchStr, cat);
    }

    /**
     * Besorgt ine Liste aller vorliegenden Kategorien
     * 
     * @return
     */
    @GET
    @Path("allcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategories() {
        return this.bbMngr.getAllCategories();
    }

    /**
     * Besorgt eine Liste aller Kategorienamen
     * 
     * @return
     */
    @GET
    @Path("allcategorynames")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllCategoryNames() {
        return this.bbMngr.getAllCategoryNames();
    }

    /**
     * Besorgt das Bild zur entsprechenden ID
     * 
     * @param imageId
     *            Id des Bildes
     * @return
     */
    @GET
    @Path("image/{imageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Image getImage(@PathParam("imageId") long imageId) {
        return this.bbMngr.getImage(imageId);
    }
}
