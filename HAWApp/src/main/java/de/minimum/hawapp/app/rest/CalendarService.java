package de.minimum.hawapp.app.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.calendar.beans.AppointmentPO;
import de.minimum.hawapp.app.calendar.beans.CategoryPO;
import de.minimum.hawapp.app.calendar.beans.LecturePO;

public class CalendarService {
    private static final String BASE_URL = "/server/rest/calendarservice/";
    private static RestTemplate restTemplate = new RestTemplate();
    static {
        final List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJacksonHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }

    public static List<CategoryPO> getCategories() {
        final String url = RestConst.HOST + BASE_URL + "categories";
        return Arrays.asList(restTemplate.getForObject(url, CategoryPO[].class));
    }

    public static List<LecturePO> getLectures(final String categoryUuid) {
        final String url = RestConst.HOST + BASE_URL + "lectures/" + categoryUuid;
        return Arrays.asList(restTemplate.getForObject(url, LecturePO[].class));
    }

    public static List<AppointmentPO> getAppointments(final String lectureUuid) {
        final String url = RestConst.HOST + BASE_URL + "appointments/" + lectureUuid;
        return Arrays.asList(restTemplate.getForObject(url, AppointmentPO[].class));
    }

}
