package de.minimum.hawapp.app.mensa.management;

import java.util.List;

import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.Lecture;

public interface CalendarManager {

    public void setCategories(List<Category> categories);

    public List<Category> getCategories();

    public List<Lecture> getLectures(Category category);

    public List<Appointment> getAppointments(Lecture lecture);

}