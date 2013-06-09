package de.minimum.hawapp.app.calendar.intern;

import java.util.List;

import android.content.Context;
import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
import de.minimum.hawapp.app.calendar.beans.Lecture;

public interface CalendarManager {

    public List<Category> getCategories();

    public List<Lecture> getLectures(Category category);

    public Lecture getLecture(String lectureUUID);

    public List<Appointment> getAppointments(Lecture lecture);

    public List<ChangeMessage> getChangeMessages(Lecture lecture);

    public CalendarAboService getCalendarAboService(Context context);

    public Category getCategory(String categoryUUID);

    public Appointment getAppointment(String appointmentUUID);

    boolean createNewAppointment(Appointment appointment, String lectureUUID, String name, String reason);

    public boolean modifyExistingAppointment(Appointment appointment, String name, String reason);

    public List<Appointment> getAppointments(String lectureUUID);

    public boolean deleteAppointment(Appointment appointment, String name, String reason);

}