package de.minimum.hawapp.server.facade.serviceprovider;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.sun.jersey.api.client.WebResource;

import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.calendar.intern.Dates;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.ChangeMessagePO;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;
//import de.minimum.hawapp.server.calendar.api.NewAppointment;

public class CalendarServiceCreateOrModifyTest extends RestTest {
    @ClassRule
    public static AutoCleanUpRule rule = new AutoCleanUpRule();
    private static final String CALENDAR_SERVICE = RestTest.REST_SERVICE_ADDRESS + "/calendarservice";
    private static CalendarManager calMgr = ManagerFactory.getManager(CalendarManager.class);
    private static CalendarParseManager calParseMgr = ManagerFactory.getManager(CalendarParseManager.class);

    private Set<? extends CategoryBO> allCategories;
    private Set<LectureBO> allLectures;

    @Before
    public void before() throws IOException {
        CalendarServiceCreateOrModifyTest.calMgr.deleteAllCalendarDataFromDB();
        CalendarServiceCreateOrModifyTest.calParseMgr.parseFromFileToDB(
                        "./src/test/java/de/minimum/hawapp/server/calendar/Sem_2012.txt", "Cp1250");

        this.allCategories = CalendarServiceCreateOrModifyTest.calMgr.getAllCategories();

        this.allLectures = new HashSet<LectureBO>();

        for(final CategoryBO category : this.allCategories) {
            final Set<? extends LectureBO> currentLectures = category.getLectures();
            this.allLectures.addAll(currentLectures);
        }
    }

    @Test
    public void createNewAppointment() {
        final LectureBO lecture = this.allLectures.iterator().next();
        final WebResource webResource = this.client.resource(CalendarServiceCreateOrModifyTest.CALENDAR_SERVICE
                        + "/appointment/new/" + lecture.getUuid());
        final AppointmentPO appointment = new AppointmentPO();
        appointment.setBegin(Dates.getDateAndTime("15.04.2012", "8:45"));
        appointment.setEnd(Dates.getDateAndTime("15.04.2012", "10:45"));
        appointment.setName("Neuer Termin");
        final ChangeMessagePO changeMessagePO = new ChangeMessagePO();
        changeMessagePO.setPerson("tester");
        changeMessagePO.setReason("zu Testgründen");
        // final NewAppointment send = new NewAppointment();
        // send.setChangeMessage(changeMessagePO);
        // send.setNewAppointment(appointment);
        // webResource.type(MediaType.APPLICATION_JSON).put(NewAppointment.class,
        // send);

        // final List<CategoryPO> categories = response.(new
        // GenericType<List<CategoryPO>>() {

    }
}
