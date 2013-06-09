package de.minimum.hawapp.app.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.AppointmentModifyDto;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
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
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public static List<Category> getCategoriesFromWeb() throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "categories";
        try {
            return Arrays.asList(restTemplate.getForObject(url, Category[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Date getCategoriesLastModifiedFromWeb() throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "categories/lastModified";
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return new Date(0);
        }
    }

    public static Category getCategory(final String categoryUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "category/" + categoryUuid;
        try {
            return restTemplate.getForObject(url, Category.class);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getCategoryLastModifiedFromWeb(final String uuid) throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "category/lastModified/" + uuid;
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return new Date(0);
        }
    }

    public static List<Lecture> getLectures(final String categoryUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "lectures/" + categoryUuid;
        try {
            return Arrays.asList(restTemplate.getForObject(url, Lecture[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Lecture getLecture(final String lectureUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "lecture/" + lectureUuid;
        try {
            return restTemplate.getForObject(url, Lecture.class);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getLectureLastModifiedFromWeb(final String uuid) throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "lecture/lastModified/" + uuid;
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return new Date(0);
        }
    }

    public static List<Appointment> getAppointments(final String lectureUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "appointments/" + lectureUuid;
        try {
            return Arrays.asList(restTemplate.getForObject(url, Appointment[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static Appointment getAppointment(final String appointmentUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "appointment/" + appointmentUuid;
        try {
            return restTemplate.getForObject(url, Appointment.class);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getAppointmentLastModifiedFromWeb(final String appointmentUUID) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "appointment/lastModified/" + appointmentUUID;
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return new Date(0);
        }
    }

    public static List<ChangeMessage> getChangeMessages(final String lectureUuid) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "changeMessages/" + lectureUuid;
        try {
            return Arrays.asList(restTemplate.getForObject(url, ChangeMessage[].class));
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static ChangeMessage getChangeMessage(final String changeMessage) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "changeMessage/" + changeMessage;
        try {
            return restTemplate.getForObject(url, ChangeMessage.class);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getChangeMessageLastModifiedFromWeb(final String uuid) throws RestClientException {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "changeMessage/lastModified/" + uuid;
        try {
            return restTemplate.getForObject(url, Date.class);
        }
        catch(final Throwable e) {
            return new Date(0);
        }
    }

    public static boolean createNewAppoinment(final AppointmentModifyDto appModifyDto, final String lectureUUID) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "lecture/" + lectureUUID + "/new";

        try {
            if (appModifyDto.getAppointment().getBegin().after(appModifyDto.getAppointment().getEnd())) {
                return false;
            }
            restTemplate.postForEntity(url, appModifyDto, null);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean modifyAppointment(final AppointmentModifyDto appointmentModifyDto) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "appointment/modify";

        try {
            if (appointmentModifyDto.getAppointment().getBegin().after(appointmentModifyDto.getAppointment().getEnd())) {
                return false;
            }
            restTemplate.postForEntity(url, appointmentModifyDto, null);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteAppointment(final AppointmentModifyDto appointmentModifyDto) {
        final String url = RestConst.HOST + CALENDARSERVICE_BASE_URL + "appointment/delete";

        try {
            restTemplate.postForEntity(url, appointmentModifyDto, null);
        }
        catch(final Throwable e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
