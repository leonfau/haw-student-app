package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MensaManager {
	
	/**
	 * @return eine Instanz des MensaManagers
	 */
	public MensaManager getInstance();

    /**
     * Aktuallisiert die Essensliste
     * Achtung, die Berwertungen werden dabei ebenfalls resettet! 
     * 
     * @throws IOException, bei keiner Verbindung zur Mensa Seite
     */
	public void update() throws IOException;
	
    /**
     * Gibt den Essensplan eines speziellen Tages als Liste zurï¿½ck.
     * @param String des Tages in Deutsch
     * @return Liste der Gerichte
     */
    public List<Meal> getDayPlan(String day);

    /**
     * Gibt die aktuelle Essensliste der Woche zurï¿½ck;
     * 
     * @return Map mit Enums fï¿½r den Tag und Listen der Gerichte
     */
    public Map<String, List<Meal>> getWeekPlan();
    
    
    /**
     * Gibt das Datum und die Zeit des letzten Updates zurück
     */
    public Date getUpdateTime();
}
