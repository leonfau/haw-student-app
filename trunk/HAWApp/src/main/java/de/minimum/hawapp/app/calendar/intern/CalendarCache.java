package de.minimum.hawapp.app.calendar.intern;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.rest.CalendarService;

public final class CalendarCache {
    private static List<Category> categories = null;
    private static Map<String, Category> categoryMap = new HashMap<String, Category>();
    private static Map<String, Lecture> lectureMap = new HashMap<String, Lecture>();
    private static Map<String, Appointment> appointmentMap = new HashMap<String, Appointment>();
    private static Map<String, ChangeMessage> changeMessageMap = new HashMap<String, ChangeMessage>();

    // public void setCategories(final List<Category> categories) {
    // CalendarCache.categories = categories;
    //
    // }

    public static synchronized List<Category> getCategories() {
        if (categories == null || categories.isEmpty()) {
            categories = CalendarService.getCategoriesFromWeb();
            if (categories != null) {
                for(final Category cat : categories) {
                    categoryMap.put(cat.getUuid(), cat);
                }
            }

            return categories;
        }
        Date nearestlastModified = null;
        for(final Category cat : categories) {
            final Date catLastModified = cat.getLastModified();
            if (nearestlastModified == null || nearestlastModified.after(catLastModified)) {
                nearestlastModified = catLastModified;
            }
        }
        final Date lastModifiedFromWeb = CalendarService.getCategoriesLastModifiedFromWeb();
        if (lastModifiedFromWeb == null || lastModifiedFromWeb.after(nearestlastModified)) {
            categories = CalendarService.getCategoriesFromWeb();
            if (categories != null) {
                for(final Category cat : categories) {
                    categoryMap.put(cat.getUuid(), cat);
                }
            }
        }
        return categories;

    }

    public static synchronized List<Lecture> getLectures(final Category category) {
        List<Lecture> lectures = category.getLectures();
        if (lectures == null || lectures.isEmpty()) {
            lectures = CalendarService.getLectures(category.getUuid());
            if (lectures != null) {
                for(final Lecture lec : lectures) {
                    lectureMap.put(lec.getUuid(), lec);
                }
            }
            category.setLectures(lectures);
        }
        else {
            final Date categoryLastModified = CalendarService.getCategoryLastModifiedFromWeb(category.getUuid());
            if (categoryLastModified.after(category.getLastModified())) {
                lectures = CalendarService.getLectures(category.getUuid());
                category.setLectures(lectures);
                if (lectures != null) {
                    for(final Lecture lec : lectures) {
                        lectureMap.put(lec.getUuid(), lec);
                    }
                }
            }
        }
        return lectures;
    }

    public static synchronized List<Appointment> getAppointments(final Lecture lecture) {
        List<Appointment> appointments = lecture.getAppointments();
        if (appointments == null || appointments.isEmpty()) {
            appointments = CalendarService.getAppointments(lecture.getUuid());
            if (appointments != null) {
                for(final Appointment app : appointments) {
                    appointmentMap.put(app.getUuid(), app);
                }
            }
            lecture.setAppointments(appointments);
        }
        else {
            final Date lectureLastModified = CalendarService.getLectureLastModifiedFromWeb(lecture.getUuid());
            if (lectureLastModified.after(lecture.getLastModified())) {
                appointments = CalendarService.getAppointments(lecture.getUuid());
                lecture.setAppointments(appointments);
                if (appointments != null) {
                    for(final Appointment app : appointments) {
                        appointmentMap.put(app.getUuid(), app);
                    }
                }
            }
        }
        return appointments;
    }

    public static synchronized List<ChangeMessage> getChangeMessages(final Lecture lecture) {
        List<ChangeMessage> changeMessages = lecture.getChangeMessages();
        if (changeMessages == null || changeMessages.isEmpty()) {
            changeMessages = CalendarService.getChangeMessages(lecture.getUuid());
            if (changeMessages != null) {
                for(final ChangeMessage msg : changeMessages) {
                    changeMessageMap.put(msg.getUuid(), msg);
                }
                lecture.setChangeMessages(changeMessages);
            }

        }
        else {
            final Date lectureLastModified = CalendarService.getLectureLastModifiedFromWeb(lecture.getUuid());
            if (lectureLastModified.after(lecture.getLastModified())) {
                changeMessages = CalendarService.getChangeMessages(lecture.getUuid());
                if (changeMessages != null) {
                    for(final ChangeMessage msg : changeMessages) {
                        changeMessageMap.put(msg.getUuid(), msg);
                    }
                    lecture.setChangeMessages(changeMessages);
                }
            }
        }
        return changeMessages;
    }

    public static Lecture getLecture(final String lectureUUID) {
        return lectureMap.get(lectureUUID);
        // Lecture lecture = null;
        // for(final Category cat : categories) {
        // for(final Lecture lec : cat.getLectures()) {
        // if (lec.getUuid().equals(lectureUUID)) {
        // lecture = lec;
        // }
        // }
        // }
        // // TODO Sicherstellen, dass nicht null zurückgegeben wird
        // return lecture;
    }

    public static Category getCategory(final String categoryUUID) {
        return categoryMap.get(categoryUUID);
        // Category category = null;
        // for(final Category cat : categories) {
        // if (cat.getUuid().equals(categoryUUID)) {
        // category = cat;
        // }
        // }
        // // TODO Sicherstellen, dass nicht null zurückgegeben wird
        // return category;
    }

    public static Appointment getAppointment(final String appointmentUUID) {
        return appointmentMap.get(appointmentUUID);
        // Appointment appointment = null;
        // for(final Category cat : categories) {
        //
        // for(final Lecture lec : cat.getLectures()) {
        // for(final Appointment app : lec.getAppointments()) {
        // if (app.getUuid().equals(appointmentUUID)) {
        // appointment = app;
        // }
        // }
        // }
        // }
        // return appointment;
    }
}
