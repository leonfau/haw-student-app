package de.minimum.hawapp.app.mensa.management;

import java.util.List;

import de.minimum.hawapp.app.mensa.beans.DayPlan;
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
    public DayPlan getDayPlan(String day);

    /**
     * Gibt den Essensplan des aktuellen Tages zurück
     * @param String des Tages in Deutsch
     * @return Liste der Gerichte
     */
    public DayPlan getPlanForToday();
    
    /**
     * Gibt die aktuelle Essensliste der Woche zur�ck;
     * 
     * @return Map mit Enums f�r den Tag und Listen der Gerichte
     */
    public List<DayPlan> getWeekPlan();
    
    /**
     * Gibt das Aktuelle Datum mit Wochentag zurück
     * 
     * @return String im Format "Wochentag, dd.mm.yyyy "
     */
    public String getTodayAsStringRepresantation();
    
    /**
     * Bewertet Essen positiv im Backend
     * @param meal stars
     */
    public void rate(Meal meal, int stars);
}
