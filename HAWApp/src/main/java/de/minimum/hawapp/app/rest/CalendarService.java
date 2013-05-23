package de.minimum.hawapp.app.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.Lecture;

public class CalendarService {
    private static final String CALENDARSERVICE_BASE_URL = "/server/rest/calendarservice/";
    private static RestTemplate restTemplate = new RestTemplate();

    static {
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }

    public static List<Category> getCategoriesFromWeb() throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "categories";
        try {
            return Arrays.asList(restTemplate.getForObject(url, Category[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public static Date getCategoriesLastModifiedFromWeb() throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "categories/lastModified";
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return null;
        }
    }

    public static Date getCategoryLastModifiedFromWeb(final String uuid) throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "category/lastModified/" + uuid;
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return null;
        }
    }

    public static List<Lecture> getLectures(final String categoryUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "lectures/" + categoryUuid;
        try {
            return Arrays.asList(restTemplate.getForObject(url, Lecture[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public static List<Appointment> getAppointments(final String lectureUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "appointments/" + lectureUuid;
        try {
            return Arrays.asList(restTemplate.getForObject(url, Appointment[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

}
