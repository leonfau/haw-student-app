package de.minimum.hawapp.app.mensa.management;

import java.util.Date;
import java.util.List;

import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.rest.CalendarService;

public class CalendarManagerImpl implements CalendarManager {
    private static List<Category> categories = null;

    public CalendarManagerImpl() {

    }

    @Override
    public void setCategories(final List<Category> categories) {
        CalendarManagerImpl.categories = categories;

    }

    @Override
    public List<Category> getCategories() {
        if (categories == null || categories.isEmpty()) {
            categories = CalendarService.getCategoriesFromWeb();
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
        }
        return categories;

    }

    @Override
    public List<Lecture> getLectures(final Category category) {
        List<Lecture> lectures = category.getLectures();
        if (lectures == null || lectures.isEmpty()) {
            lectures = CalendarService.getLectures(category.getUuid());
            category.setLectures(lectures);
        }
        else {
            final Date categoryLastModified = CalendarService.getCategoryLastModifiedFromWeb(category.getUuid());
            if (categoryLastModified.after(category.getLastModified())) {
                lectures = CalendarService.getLectures(category.getUuid());
                category.setLectures(lectures);
            }
        }
        return lectures;
    }

    @Override
    public List<Appointment> getAppointments(final Lecture lecture) {
        return CalendarService.getAppointments(lecture.getUuid());
    }

}
