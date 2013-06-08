package de.minimum.hawapp.app.calendar.intern;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import de.minimum.hawapp.app.calendar.beans.Lecture;

public final class LectureSubscribtion implements Serializable {
    @Override
    public String toString() {
        return lectureName;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 876831189832189334L;

    private final String lectureUUID;
    private final Date lastModified;
    private final String lectureName;
    private final String lecturerName;
    private final long calendarID;
    private List<AppointmentSubscribtion> appointments;

    public LectureSubscribtion(final Lecture lecture, final long calendarID) {
        lectureUUID = lecture.getUuid();
        lectureName = lecture.getName();
        lecturerName = lecture.getLecturerName();
        lastModified = lecture.getLastModified();
        this.calendarID = calendarID;
    }

    public String getLectureUUID() {
        return lectureUUID;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public List<AppointmentSubscribtion> getAppointments() {
        return appointments;
    }

    public void setAppointments(final List<AppointmentSubscribtion> appointments) {
        this.appointments = appointments;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public long getCalendarID() {
        return calendarID;
    }

}
