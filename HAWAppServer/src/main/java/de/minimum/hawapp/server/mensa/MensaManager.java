package de.minimum.hawapp.server.mensa;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
    public DayPlan getDayPlan(Day day);


    /**
     * Gibt die aktuelle Essensliste der Woche zurück;
     * 
     * @return Liste von DayPlan
     */
    public List<DayPlan> getWeekPlan();
    
    /**
     * @return  Datum und die Zeit des letzten Updates 
     */
    public Date getUpdateTime();
    
    /**
     * Bewertet das Gericht mit einer Stimme positiv
     * @param id: id des Gerichts, das Bewertet wird
     * @return Prozent der positiven Bewertungen (zwischen 0 und 100)
     */
    public int rateMealPositive(UUID id);
    
    /**
     * Bewertet das Gericht mit einer Stimme negativ
     * @param id: id des Gerichts, das Bewertet wird
     * @return Prozent der positiven Bewertungen (zwischen 0 und 100)
     */
    public int rateMealNegative(UUID id);
    
    /**
     * 
     * @param id des Gerichts
     * @return Bewertung des Gerichts als Prozent positiver Rating zwisch 0 und 100
     */
    public int getMealRaiting(UUID id);
    
  
}
