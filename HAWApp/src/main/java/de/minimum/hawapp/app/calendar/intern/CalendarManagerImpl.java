package de.minimum.hawapp.app.calendar.intern;

import java.util.List;

import android.content.Context;
import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.AppointmentModifyDto;
import de.minimum.hawapp.app.calendar.beans.Category;
import de.minimum.hawapp.app.calendar.beans.ChangeMessage;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.rest.CalendarService;

public class CalendarManagerImpl implements CalendarManager {
    private CalendarAboService calendarAboService = null;

    public CalendarManagerImpl() {

    }

    @Override
    public List<Category> getCategories() {
        return CalendarCache.getCategories();
    }

    @Override
    public List<Lecture> getLectures(final Category category) {
        return CalendarCache.getLectures(category);
    }

    @Override
    public List<Appointment> getAppointments(final Lecture lecture) {
        return CalendarCache.getAppointments(lecture);
    }

    @Override
    public List<Appointment> getAppointments(final String lectureUUID) {
        return CalendarCache.getAppointments(lectureUUID);
    }

    @Override
    public CalendarAboService getCalendarAboService(final Context context) {
        if (calendarAboService == null) {
            calendarAboService = CalendarAboService.getSavedCalendarAboService(context);

        }
        if (calendarAboService == null) {
            calendarAboService = new CalendarAboService(context);
        }
        return calendarAboService;
    }

    @Override
    public Lecture getLecture(final String lectureUUID) {
        return CalendarCache.getLecture(lectureUUID);
    }

    @Override
    public Category getCategory(final String categoryUUID) {
        return CalendarCache.getCategory(categoryUUID);
    }

    @Override
    public Appointment getAppointment(final String appointmentUUID) {
        return CalendarCache.getAppointment(appointmentUUID);
    }

    @Override
    public List<ChangeMessage> getChangeMessages(final Lecture lecture) {
        return CalendarCache.getChangeMessages(lecture);
    }

    @Override
    public boolean createNewAppointment(final Appointment appointment, final String lectureUUID, final String name,
                    final String reason) {
        final AppointmentModifyDto appModifyDto = new AppointmentModifyDto();
        appModifyDto.setAppointment(appointment);
        appModifyDto.setFromPerson(name);
        appModifyDto.setReason(reason);
        return CalendarService.createNewAppoinment(appModifyDto, lectureUUID);

    }

    @Override
    public boolean modifyExistingAppointment(final Appointment appointment, final String name, final String reason) {
        final AppointmentModifyDto appModifyDto = new AppointmentModifyDto();
        appModifyDto.setAppointment(appointment);
        appModifyDto.setFromPerson(name);
        appModifyDto.setReason(reason);
        return CalendarService.modifyAppointment(appModifyDto);
    }

    @Override
    public boolean deleteAppointment(final Appointment appointment, final String name, final String reason) {
        final AppointmentModifyDto appModifyDto = new AppointmentModifyDto();
        appModifyDto.setAppointment(appointment);
        appModifyDto.setFromPerson(name);
        appModifyDto.setReason(reason);
        return CalendarService.deleteAppointment(appModifyDto);
    }

}
