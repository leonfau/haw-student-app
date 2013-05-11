package de.minimum.hawapp.app.mensa.management;

import java.util.Calendar;
import java.util.List;

import de.minimum.hawapp.app.mensa.beans.DayPlan;
import de.minimum.hawapp.app.rest.MensaService;

public class MensaManagerImpl implements MensaManager {
   
	MensaService mensaService;

    public MensaManagerImpl() {
    	mensaService = new MensaService();
    }

    @Override
    public DayPlan getDayPlan(String day) {
        return mensaService.getDayPlan(day);
    }
    
    @Override
    public DayPlan getPlanForToday() {
    	Calendar calendar = Calendar.getInstance();
    	int day = calendar.get(Calendar.MONDAY);
    	
    	switch (day) {
    	case Calendar.MONDAY: return getDayPlan("Montag");
    	case Calendar.TUESDAY: return getDayPlan("Dienstag");
    	case Calendar.WEDNESDAY: return getDayPlan("Mittwoch");
    	case Calendar.THURSDAY: return getDayPlan("Donnerstag");
    	case Calendar.FRIDAY: return getDayPlan("Freitag");
    	default: return null;
    	}
    }
    
    @Override
    public List<DayPlan> getWeekPlan() {
    	return (List<DayPlan>) mensaService.getWeekPlan();
    }
}
