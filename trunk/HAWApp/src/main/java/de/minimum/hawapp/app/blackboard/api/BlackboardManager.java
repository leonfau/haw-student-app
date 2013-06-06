package de.minimum.hawapp.app.blackboard.api;

import java.io.File;
import java.util.List;

import android.content.Context;
import de.minimum.hawapp.app.blackboard.exceptions.OfferCreationFailedException;

public interface BlackboardManager {

    /**
     * Besorgt alle Angebote die selber eingestellt wurden
     * 
     * @return
     */
    public List<Offer> getAllOwnOffers(Context context);

    /**
     * Entfernt das übergebene Angebot
     * 
     * @param offer
     * @return false wenn das Angebot nicht entfernt werden konnte. Unter
     *         anderem auch wenn das Angebot nicht von einem selber erstellt
     *         wurde
     */
    public boolean removeOwnOffer(Context context, Offer offer);

    /**
     * Setzt das Angebot auf eine IgnoreList. Das Angebot wird in der Folge
     * nicht mehr vom Manager zurückgegeben
     * 
     * @param offer
     * @return
     */
    public boolean ignoreOffer(Context context, Offer offer);

    /**
     * Entfernt das Angebot von der IgnoreList. Es wird in der Folge wieder vom
     * Manager zurückgegeben
     * 
     * @param offer
     * @return true wenn entfernen erfolgreich sonst false. Auch false wenn
     *         Offer nicht auf der IgnoreList
     */
    public boolean unignoreOffer(Context context, Offer offer);

    public List<Offer> getIgnoredOffers(Context context);

    public List<Offer> getAllOffers(Context context);

    public List<Offer> searchOffers(Context context, String searchString);

    public Category getCategory(Context context, String categoryName);

    public Offer getOfferById(Context context, Long id);

    public List<String> getAllCategoryNames(Context context);

    public Image getImageById(Context context, Long id);

    /**
     * 
     * @param category
     * @param header
     * @param description
     * @param contact
     * @param image
     * @return Die Id des Erzeugten Offer.
     * @throws OfferCreationFailedException
     *             Wenn das Offer nicht angelegt werden konnte
     */
    public Long createOffer(Context context, String category, String header, String description, String contact,
                    File image) throws OfferCreationFailedException;
}
