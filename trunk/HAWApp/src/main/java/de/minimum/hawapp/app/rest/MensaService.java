package de.minimum.hawapp.app.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.mensa.beans.DayPlan;

public class MensaService {

    private static final String BASE_URL = "/server/rest/mensaservice/";

    /**
     * Ermittelt den Speiseplan für einen bestimmten Tag
     * 
     * @param day
     *            Tag für den der Speiseplan ermittelt werden soll
     *            ("Montag","Dienstag","Mittwoch","Donnerstag","Freitag")
     * @return Eine Liste der Speisen des Tages
     */
    public DayPlan getDayPlan(final String day) {
        final RestTemplate restTemplate = new RestTemplate();
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);

        final String url = RestConst.HOST + BASE_URL + "dayplan/" + day;
        return restTemplate.getForObject(url, DayPlan.class);
    }

    /**
     * Ermittelt den Speiseplan für die aktuelle Woche
     * 
     * @return Eine Map ('Wochentag' => 'Liste der Speisen')
     */
    public List<? extends DayPlan> getWeekPlan() {
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        final String url = RestConst.HOST + BASE_URL + "weekplan";
        return Arrays.asList(restTemplate.getForObject(url, DayPlan[].class));
    }
}
