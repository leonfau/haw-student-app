package de.minimum.hawapp.app.calendar.intern;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import de.minimum.hawapp.app.calendar.beans.Appointment;
import de.minimum.hawapp.app.calendar.beans.Lecture;
import de.minimum.hawapp.app.calendar.provider.Calendar;
import de.minimum.hawapp.app.calendar.provider.CalendarProvider;
import de.minimum.hawapp.app.calendar.provider.CalendarProviderImpl;
import de.minimum.hawapp.app.calendar.provider.Event;
import de.minimum.hawapp.app.calendar.provider.EventImpl;
import de.minimum.hawapp.app.context.ManagerFactory;
import de.minimum.hawapp.app.rest.CalendarService;

public class CalendarAboService implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6463251438511431991L;
    private final static String CALENDAR_ABO_FILENAME = "calAboPersistenceData.per";

    private final List<LectureSubscribtion> subscribtions = new ArrayList<LectureSubscribtion>();

    private transient Context context;
    private transient CalendarProvider calprovider;
    private transient CalendarManager calManager;

    CalendarAboService(final Context context) {
        this.context = context;
        calprovider = new CalendarProviderImpl(context);
        calManager = ManagerFactory.getManager(CalendarManager.class);
    }

    public void subscribeLecture(final Lecture lecture, final Calendar calendar) {

        final LectureSubscribtion lectureSubscription = new LectureSubscribtion(lecture, calendar.getID());
        final List<Appointment> appointments = calManager.getAppointments(lecture);
        final List<AppointmentSubscribtion> appointmentSubscription = new ArrayList<AppointmentSubscribtion>();
        for(final Appointment appointment : appointments) {
            final AppointmentSubscribtion appSub = addAppointment(calendar.getID(), appointment);
            appointmentSubscription.add(appSub);
        }
        lectureSubscription.setAppointments(appointmentSubscription);
        subscribtions.add(lectureSubscription);
        try {
            persist();
        }
        catch(final IOException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribeLecture(final String lectureUUID) {
        final Iterator<LectureSubscribtion> it = subscribtions.iterator();
        while(it.hasNext()) {
            final LectureSubscribtion actualSubscription = it.next();
            if (actualSubscription.getLectureUUID().equals(lectureUUID)) {
                for(final AppointmentSubscribtion appointmentSubscription : actualSubscription.getAppointments()) {
                    calprovider.deleteEventById(appointmentSubscription.getAppointmentID());
                }
                it.remove();
            }
        }
    }

    public void synchroniseSubscriptedLectures() {
        for(final LectureSubscribtion lectureSub : subscribtions) {
            synchroniseSubscriptedLecture(lectureSub);
        }
        try {
            persist();
        }
        catch(final IOException e) {
            e.printStackTrace();
        }
    }

    private void synchroniseSubscriptedLecture(final LectureSubscribtion lectureSub) {
        final String lectureUUID = lectureSub.getLectureUUID();
        final Date lastModifiedFromWeb = CalendarService.getLectureLastModifiedFromWeb(lectureUUID);
        if (lastModifiedFromWeb.after(lectureSub.getLastModified())) {
            final List<Appointment> appointments = calManager.getAppointments(lectureUUID);
            final Map<String, Appointment> map = new HashMap<String, Appointment>();
            for(final Appointment appointment : appointments) {
                map.put(appointment.getUuid(), appointment);
            }
            final List<AppointmentSubscribtion> appSub = lectureSub.getAppointments();
            final Iterator<AppointmentSubscribtion> it = appSub.iterator();
            while(it.hasNext()) {
                final AppointmentSubscribtion app = it.next();
                if (map.containsKey(app.getUuid())) {
                    if (map.get(app.getUuid()).getLastModified().after(app.getLastModified())) {
                        modifyAppointment(app.getAppointmentID(), map.get(app.getUuid()));
                    }
                    map.remove(app.getUuid());
                }
                else {
                    calprovider.deleteEventById(app.getAppointmentID());
                    it.remove();
                }
            }

            for(final Appointment appointment : map.values()) {
                final AppointmentSubscribtion apppSubscription = addAppointment(lectureSub.getCalendarID(), appointment);
                appSub.add(apppSubscription);
            }

        }
    }

    private void modifyAppointment(final long appointementID, final Appointment appointment) {
        final Event event = new EventImpl(appointementID, appointment.getName(), appointment.getBegin().getTime(),
                        appointment.getEnd().getTime());
        event.setDescription(appointment.getDetails());
        event.setLocation(appointment.getLocation());
        calprovider.updateEvent(event);

    }

    private AppointmentSubscribtion addAppointment(final long calendarID, final Appointment appointment) {
        final Event event = new EventImpl(appointment.getName(), appointment.getBegin().getTime(), appointment.getEnd()
                        .getTime());
        event.setDescription(appointment.getDetails());
        event.setLocation(appointment.getLocation());
        final long appointmentID = calprovider.addEvent(calendarID, event);
        return AppointmentSubscribtion.newInstance(appointment, appointmentID);

    }

    public List<LectureSubscribtion> getSubscribtedLectures() {
        return subscribtions;
    }

    public boolean isSubscribted(final String lectureUUID) {
        for(final LectureSubscribtion sub : subscribtions) {
            if (sub.getLectureUUID().equals(lectureUUID)) {
                return true;
            }
        }
        return false;
    }

    public Set<Calendar> getCalendars() {
        return calprovider.getCalendars();
    }

    static CalendarAboService getSavedCalendarAboService(final Context context) {
        CalendarAboService aboService = null;
        FileInputStream f_in = null;
        try {
            // Read from disk using FileInputStream
            f_in = context.openFileInput(CALENDAR_ABO_FILENAME);

            final ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Read an object

            final Object obj = obj_in.readObject();

            if (obj instanceof CalendarAboService) {
                // Cast object to a Vector
                aboService = (CalendarAboService)obj;
                aboService.context = context;
                aboService.calprovider = new CalendarProviderImpl(context);
                aboService.calManager = ManagerFactory.getManager(CalendarManager.class);
            }
        }
        catch(final Throwable e) {
            e.printStackTrace();
        }
        finally {
            try {
                f_in.close();
            }
            catch(final Throwable e) {
                // nothing to catch
            }
        }
        return aboService;
    }

    public void persist() throws IOException {
        // Write to disk with FileOutputStream
        final FileOutputStream f_out = context.openFileOutput(CALENDAR_ABO_FILENAME, Context.MODE_PRIVATE);

        // Write object with ObjectOutputStream
        final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

        // Write object out to disk
        obj_out.writeObject(this);
        obj_out.flush();
        obj_out.close();
    }
}
