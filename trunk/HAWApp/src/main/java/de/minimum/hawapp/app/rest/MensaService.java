package de.minimum.hawapp.app.rest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.mensa.beans.Meal;

public class MensaService {

	private static final String HOST = "Http://192.168.1.9:8080";
	private static final String BASE_URL = "/server/rest/mensaservice/";
	
	    /**
	     * Ermittelt den Speiseplan für einen bestimmten Tag
	     * 
	     * @param day
	     *            Tag für den der Speiseplan ermittelt werden soll
	     *            ("Montag","Dienstag","Mittwoch","Donnerstag","Freitag")
	     * @return Eine Liste der Speisen des Tages
	     */
	    public List<Meal> getDayPlan(String day) {
	    	System.out.println("REST ZEUG");
	    	RestTemplate restTemplate = new RestTemplate();
	        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	        String url = HOST + BASE_URL + "dayplan/"+day;
	        System.out.println(url);
	        return Arrays.asList(restTemplate.getForObject(url, Meal[].class));
	    }

	    /**
	     * Ermittelt den Speiseplan für die aktuelle Woche
	     * 
	     * @return Eine Map ('Wochentag' => 'Liste der Speisen')
	     */
	    public Map<String, List<Meal>> getWeekPlan() {
	    	//TODO Muss geklärt werden ob Map<String, Liste<Meal>> möglich ist/Sinn macht oder ob eine
	    	//Datenstruktur draufgelegt werden sollte
	        return null;
	    }
}

