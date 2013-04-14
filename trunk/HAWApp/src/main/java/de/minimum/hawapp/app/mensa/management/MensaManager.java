package de.minimum.hawapp.app.mensa.management;

import java.util.List;
import java.util.Map;

import de.minimum.hawapp.app.mensa.beans.Meal;

/**
 * Managementinterface um GUI vom Restservice zu trennen.
 * Übernimmt die Logik als Controller
 */
public interface MensaManager {
	
    /**
     * Gibt den Essensplan eines speziellen Tages als Liste zur�ck.
     * @param String des Tages in Deutsch
     * @return Liste der Gerichte
     */
    public List<Meal> getDayPlan(String day);

    /**
     * Gibt den Essensplan des aktuellen Tages zurück
     * @param String des Tages in Deutsch
     * @return Liste der Gerichte
     */
    public List<Meal> getPlanForToday();
    
    /**
     * Gibt die aktuelle Essensliste der Woche zur�ck;
     * 
     * @return Map mit Enums f�r den Tag und Listen der Gerichte
     */
    public Map<String, List<Meal>> getWeekPlan();
}
