package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MensaPlan {

    /**
     * Parst die Webseite neu
     * 
     * @throws IOException
     *             , bei keiner Verbindung zur Mensa Seite
     */
    public void update() throws IOException;

    /**
     * Gibt den Essensplan eines speziellen Tages als Liste zurück.
     * 
     * @param Enum
     *            day
     * @return Liste der Gerichte
     */
    public List<Meal> getDayPlan(String day);

    /**
     * Gibt die aktuelle Essensliste der Woche zurück;
     * 
     * @return Map mit Enums für den Tag und Listen der Gerichte
     */
    public Map<String, List<Meal>> getWeekPlan();
    
    /**
     * Datum und Zeit des zuletzt durchgeführten updates
     */
    public Date getUpdateTime();
    
    /**
     * 
     * @param id: UUID des Gerichts
     * @return: das Gericht
     */
    public Meal getMealByID(UUID id);
}
