package de.minimum.hawapp.app.calendar.intern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Lecture;

public final class LectureSubscribtion implements Serializable {
    @Override
    public String toString() {
        return lecturerName;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 876831189832189334L;

    private final String lectureUUID;
    private final String lectureName;
    private final String lecturerName;
    private final List<String> appointmentUUIDs;

    public LectureSubscribtion(final Lecture lecture) {
        lectureUUID = lecture.getUuid();
        lectureName = lecture.getName();
        lecturerName = lecture.getLecturerName();
        appointmentUUIDs = new ArrayList<String>();
        for(final Appointment appointment : lecture.getAppointments()) {
            appointmentUUIDs.add(appointment.getUuid());
        }
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

    public List<String> getAppointmentUUIDs() {
        return appointmentUUIDs;
    }
}
