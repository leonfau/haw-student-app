package de.minimum.hawapp.server.facade.serviceprovider;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

    @POST
    @Path("newoffer/category/{cat}/header/{header}/description/{descr: .*}/contact/{contact: .*}/price/{price: .*}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public OfferCreationStatus newOffer(@PathParam("cat") String category, @PathParam("header") String header,
                    @PathParam("descr") String descr, @PathParam("contact") String contact,
                    @PathParam("price") double price, @FormDataParam("image") InputStream uploadedImgStream) {
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
        return this.bbMngr.createOffer(category, header, descr, contact, price, img);
    }

    @POST
    @Path("newoffer/category/{cat}/header/{header}/description/{descr}/contact/{contact}/price/{price}")
    @Produces(MediaType.APPLICATION_JSON)
    public OfferCreationStatus newOffer(@PathParam("cat") String category, @PathParam("header") String header,
                    @PathParam("descr") String descr, @PathParam("contact") String contact,
                    @PathParam("price") double price) {
        return newOffer(category, header, descr, contact, price, null);
    }

    @DELETE
    @Path("remove/offerid/{offerId}/deletionkey/{delKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean removeOffer(@PathParam("offerId") long offerId, @PathParam("delKey") String delKey) {
        return this.bbMngr.removeOffer(offerId, delKey);
    }

    @POST
    @Path("report/offerid/{offerId}/reason/{reason}")
    public Response report(@PathParam("offerId") long offerId, @PathParam("reason") String reason) {
        this.bbMngr.reportOffer(offerId, reason);
        return Response.ok().build();
    }

    @GET
    @Path("alloffers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getAllOffers() {
        return this.bbMngr.getAllOffers();
    }

    @GET
    @Path("offer/{offerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Offer getOffer(@PathParam("offerId") long offerId) {
        return this.bbMngr.getOffer(offerId);
    }

    @GET
    @Path("category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategory(@PathParam("category") String cat) {
        return this.bbMngr.getCategory(cat);
    }

    @GET
    @Path("search/{searchStr}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Offer> getOffersBySearchStr(@PathParam("searchStr") String searchStr) {
        return this.bbMngr.getOffersBySearchStr(searchStr);
    }

    @GET
    @Path("search/{searchStr}/category/{category}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getOffersBySearchStrAndCategory(@PathParam("searchStr") String searchStr,
                    @PathParam("category") String cat) {
        return this.bbMngr.getOffersBySearchStrAndCategory(searchStr, cat);
    }

    @GET
    @Path("allcategories")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCategories() {
        return this.bbMngr.getAllCategories();
    }

    @GET
    @Path("allcategorynames")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getAllCategoryNames() {
        return this.bbMngr.getAllCategoryNames();
    }

    @GET
    @Path("image/{imageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Image getImage(@PathParam("imageId") long imageId) {
        return this.bbMngr.getImage(imageId);
    }
}
