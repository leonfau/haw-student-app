package de.minimum.hawapp.app.calendar.beans;

import java.io.Serializable;

public class AppointmentModifyDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9170130756609721126L;

    private Appointment appointment;
    private String fromPerson;
    private String reason;

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(final Appointment appointment) {
        this.appointment = appointment;
    }

    public String getFromPerson() {
        return fromPerson;
    }

    public void setFromPerson(final String fromPerson) {
        this.fromPerson = fromPerson;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

}
