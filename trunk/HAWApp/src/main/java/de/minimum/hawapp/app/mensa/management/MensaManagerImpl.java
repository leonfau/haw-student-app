package de.minimum.hawapp.app.mensa.management;

import java.text.SimpleDateFormat;
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
    
    @Override
    public String getTodayAsStringRepresantation() {
    	Calendar calendar = Calendar.getInstance();
    	int day = calendar.get(Calendar.DAY_OF_WEEK);
    	
    	StringBuilder today = new StringBuilder();
    	switch (day) {
    	case Calendar.MONDAY: today.append("Montag"); break;
    	case Calendar.TUESDAY: today.append("Dienstag"); break;
    	case Calendar.WEDNESDAY: today.append("Mittwoch"); break;
    	case Calendar.THURSDAY: today.append("Donnerstag"); break;
    	case Calendar.FRIDAY: today.append("Freitag"); break;
    	case Calendar.SATURDAY: today.append("Samstag"); break;
    	case Calendar.SUNDAY: today.append("Sonntag"); break;
    	}
    	
    	today.append(", ");
    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    	today.append(format.format(calendar.getTime()));
    	return today.toString();
    }
}
