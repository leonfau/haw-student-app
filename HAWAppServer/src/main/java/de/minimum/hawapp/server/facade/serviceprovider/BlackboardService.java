package de.minimum.hawapp.server.facade.serviceprovider;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.resource.Singleton;

import de.minimum.hawapp.server.blackboard.api.BlackboardManager;
import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Image;
import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.util.OfferCreationStatus;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.facade.util.ServiceProviderLogger;

@Singleton
@Path("/blackboardservice")
public class BlackboardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlackboardService.class);
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
                    @FormDataParam("contact") String contact, @FormDataParam("image") InputStream uploadedImgStream,
                    @Context HttpServletRequest req) {
        // @FormDataParam("image") FormDataContentDisposition fileDetail -> zu
        // den Parametern hinzu fallse Deatils zu der Datei benötigt werden
        ServiceProviderLogger.logRequest("Creation of offer \"" + header + "\" creation in Category " + category,
                        BlackboardService.LOGGER, req);
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
    public boolean removeOffer(@PathParam("offerId") long offerId, @PathParam("delKey") String delKey,
                    @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Trial to remove offer with id: " + offerId + " with key: " + delKey,
                        BlackboardService.LOGGER, req);
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
    public Response report(@FormParam("offerId") long offerId, @FormParam("reason") String reason,
                    @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Report of offer with id: " + offerId, BlackboardService.LOGGER, req);
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
    public List<Offer> getAllOffers(@Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for all offers", BlackboardService.LOGGER, req);
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
    public Offer getOffer(@PathParam("offerId") long offerId, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for offer with id: " + offerId, BlackboardService.LOGGER, req);
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
    public Category getCategory(@PathParam("category") String cat, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for category: " + cat, BlackboardService.LOGGER, req);
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
    public List<Offer> getOffersBySearchStr(@PathParam("searchStr") String searchStr, @Context HttpServletRequest req) {
        String searchString = null;
        try {
            searchString = URLDecoder.decode(searchStr, req.getCharacterEncoding());
        }
        catch(UnsupportedEncodingException e) {
            BlackboardService.LOGGER.error(
                            "Unable to encode: " + searchStr + " with encoding: " + req.getCharacterEncoding(), e);
            // TODO was tun?
            e.printStackTrace();
        }
        ServiceProviderLogger.logRequest("Searchrequest for: " + searchString, BlackboardService.LOGGER, req);
        return this.bbMngr.getOffersBySearchStr(searchString);
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
                    @PathParam("category") String cat, @Context HttpServletRequest req) {
        String searchString = null;
        try {
            searchString = URLDecoder.decode(searchStr, req.getCharacterEncoding());// TODO
                                                                                    // auch
                                                                                    // category
                                                                                    // encoden?
        }
        catch(UnsupportedEncodingException e) {
            BlackboardService.LOGGER.error(
                            "Unable to encode: " + searchStr + " with encoding: " + req.getCharacterEncoding(), e);
            // TODO was tun?
            e.printStackTrace();
        }
        ServiceProviderLogger.logRequest("Searchrequest for: " + searchString + " in category: " + cat,
                        BlackboardService.LOGGER, req);
        return this.bbMngr.getOffersBySearchStrAndCategory(searchString, cat);
    }

    /**
     * Besorgt ine Liste aller vorliegenden Kategorien
     * 
     * @return
     */
    @GET
    @Path("allcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategories(@Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for all Categories", BlackboardService.LOGGER, req);
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
    public List<String> getAllCategoryNames(@Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for categorynames", BlackboardService.LOGGER, req);
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
    public Image getImage(@PathParam("imageId") long imageId, @Context HttpServletRequest req) {
        ServiceProviderLogger.logRequest("Request for image with id: " + imageId, BlackboardService.LOGGER, req);
        return this.bbMngr.getImage(imageId);
    }
}
