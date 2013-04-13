package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MensaManager {
    /**
     * Aktuallisiert die Essensliste
     * Achtung, die Berwertungen werden dabei ebenfalls resettet! 
     * 
     * @throws IOException, bei keiner Verbindung zur Mensa Seite
     */
	public void update() throws IOException;
	
	/**
	 * Startet den Update Task als Deamon
	 * @param firstTime, Datum und Zeit der ersten Ausführung
	 * @param period, Zeitabstand der wiederholten Ausführung in ms
	 */
	public void startScheduledUpdate(Date firstTime, long period);
	
	/**
	 * Stoppt die Automatische aktualisierung
	 */
	public void stopScheduledUpdate();
	  
    /**
     * Gibt den Essensplan eines speziellen Tages als Liste zurück.
     * @param String des Tages in Deutsch
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
     * Gibt das Datum und die Zeit des letzten Updates zurück
     */
    public Date getUpdateTime();
    
  
}
