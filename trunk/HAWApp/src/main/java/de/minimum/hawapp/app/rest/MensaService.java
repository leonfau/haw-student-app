package de.minimum.hawapp.app.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.mensa.beans.DayPlan;

public class MensaService {

    private static final String BASE_URL = "/server/rest/mensaservice/";
    private final RestTemplate restTemplate;
    private final List<HttpMessageConverter<?>> messageConverters;

    public MensaService() {
        this.restTemplate = new RestTemplate();
        this.messageConverters = new ArrayList<HttpMessageConverter<?>>();
        this.messageConverters.add(new FormHttpMessageConverter());
        this.messageConverters.add(new StringHttpMessageConverter());
        this.messageConverters.add(new MappingJacksonHttpMessageConverter());
        this.restTemplate.setMessageConverters(this.messageConverters);
    }

    /**
     * Ermittelt den Speiseplan für einen bestimmten Tag
     * 
     * @param day
     *            Tag für den der Speiseplan ermittelt werden soll
     *            ("Montag","Dienstag","Mittwoch","Donnerstag","Freitag")
     * @return Eine Liste der Speisen des Tages
     */
    public DayPlan getDayPlan(final String day) {
        final String url = RestConst.HOST + MensaService.BASE_URL + "dayplan/" + day;
        try {
            return this.restTemplate.getForObject(url, DayPlan.class);
        }
        catch(RestClientException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Ermittelt den Speiseplan für die aktuelle Woche
     * 
     * @return Eine Map ('Wochentag' => 'Liste der Speisen')
     */
    public List<? extends DayPlan> getWeekPlan() {
        final String url = RestConst.HOST + MensaService.BASE_URL + "weekplan";
        try {
            return Arrays.asList(this.restTemplate.getForObject(url, DayPlan[].class));
        }
        catch(RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void ratePositive(UUID mealId) {
        final String url = RestConst.HOST + MensaService.BASE_URL + "rate/" + mealId + "/stars/" + 5;
        try {
            this.restTemplate.postForObject(url, null, ResponseEntity.class);
        }
        catch(RestClientException e) {
            e.printStackTrace();
        }
    }

    public void rateNegative(UUID mealId) {
        final String url = RestConst.HOST + MensaService.BASE_URL + "negativerate/" + mealId;
        try {
            this.restTemplate.put(url, null);
        }
        catch(RestClientException e) {
            e.printStackTrace();
        }
    }
}
