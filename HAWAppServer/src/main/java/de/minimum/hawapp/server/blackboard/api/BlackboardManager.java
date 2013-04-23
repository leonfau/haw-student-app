package de.minimum.hawapp.server.blackboard.api;

import java.util.List;

import de.minimum.hawapp.server.blackboard.util.OfferCreationStatus;

public interface BlackboardManager {

    /**
     * Erstellt eine neues Angebot.
     * 
     * @param category
     *            Kategorie in dem das Angebot liegt -> diese muss bereits
     *            vorliegen. Es wird keine neue Kategorie erzeugt
     * @param header
     *            Überschrift des Angebots
     * @param description
     *            Beschreibung des Angebots -> darf null sein
     * @param contact
     *            Kontaktdaten -> darf null sein
     * @param price
     *            der geforderte Preis -> darf null sein
     * @param image
     *            Bild zu dem Angebot werden -> darf null sein
     * @return Ein Statusobject mit Information dazu ob erfolgreich, dem
     *         deletionKey und dem erzeugtem Angebot
     */
    OfferCreationStatus createOffer(String category, String header, String description, String contact, double price,
                    byte[] image);

    /**
     * Löscht das Angebiot mit der entsprechenden Id
     * 
     * @param offerId
     *            Id des Angebots zum löschen
     * @param deletionKey
     *            Schlüssel der einen Autorisiert das Angebot zu löschen ->
     *            diesen hat man beim Erstellen des Angebots erhalten
     * @return true wenn das Löschen erfolgreich war
     */
    boolean removeOffer(long offerId, String deletionKey);

    /**
     * Meldet ein Angebot
     * 
     * @param offerId
     *            Id des Angebots das gemeldet wird
     * @param reason
     *            Begründung warum das Angebot gemeldet wurde
     * @return
     */
    void reportOffer(long offerId, String reason);

    /**
     * Besorgt eine Liste mit allen vorliegenden Angeboten
     * 
     * @return
     */
    List<Offer> getAllOffers();

    /**
     * Besorgt das Angebot mit entsprechender Id
     * 
     * @param offerId
     *            Id des Angebots
     * @return
     */
    Offer getOffer(long offerId);

    /**
     * Basorgt eine Kategorie mit allen in ihr vorliegenden Angeboten
     * 
     * @param category
     *            Name der zu besorgenden Kategorie
     * @return
     */
    Category getCategory(String category);

    /**
     * Sucht nach Angeboten
     * 
     * @param searchStr
     *            Sucheingabe nach der in den Angeboten gesucht werden soll
     * @return Liste aller zutreffenden Angebote
     */
    List<Offer> getOffersBySearchStr(String searchStr);

    /**
     * Sucht nach Angeboten
     * 
     * @param searchStr
     *            Sucheingabe nach der in den Angeboten gesucht werden soll
     * @param category
     *            Kategorie in der nachgeschaut werden soll
     * @return Liste aller zutreffenden Angebote
     */
    List<Offer> getOffersBySearchStrAndCategory(String searchStr, String category);

    /**
     * Besorgt ine Liste aller vorliegenden Kategorien
     * 
     * @return
     */
    List<Category> getAllCategories();

    /**
     * Besorgt eine Liste aller Kategorienamen
     * 
     * @return
     */
    List<String> getAllCategoryNames();

    /**
     * Besorgt das Bild zur entsprechenden ID
     * 
     * @param imageId
     *            Id des Bildes
     * @return
     */
    Image getImage(long imageId);
}
