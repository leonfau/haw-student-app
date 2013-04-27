package de.minimum.hawapp.server.facade.serviceprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.CategoryPO;
import de.minimum.hawapp.server.persistence.calendar.ChangeMessagePO;
import de.minimum.hawapp.server.persistence.calendar.LecturePO;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;

public class CalendarServiceTest extends RestTest {
    @ClassRule
    public static AutoCleanUpRule rule = new AutoCleanUpRule();
    private static final String CALENDAR_SERVICE = RestTest.REST_SERVICE_ADDRESS + "/calendarservice";
    private static CalendarManager calMgr = ManagerFactory.getManager(CalendarManager.class);
    private static CalendarParseManager calParseMgr = ManagerFactory.getManager(CalendarParseManager.class);

    private static Set<? extends CategoryBO> allCategories;
    private static Set<LectureBO> allLectures;
    private static Set<AppointmentBO> allAppointments;
    private static Set<ChangeMessageBO> allChangeMessages;

    @BeforeClass
    public static void before() throws IOException {
        calMgr.deleteAllCalendarDataFromDB();
        calParseMgr.parseFromFileToDB("./src/test/java/de/minimum/hawapp/server/calendar/Sem_2012.txt", "Cp1250");

        allCategories = calMgr.getAllCategories();

        allLectures = new HashSet<LectureBO>();
        allAppointments = new HashSet<AppointmentBO>();
        allChangeMessages = new HashSet<ChangeMessageBO>();
        for(final CategoryBO category : allCategories) {
            final Set<? extends LectureBO> currentLectures = category.getLectures();
            allLectures.addAll(currentLectures);
            for(final LectureBO lecture : currentLectures) {
                final Set<? extends AppointmentBO> currentAppointments = lecture.getAppointments();
                final Set<? extends ChangeMessageBO> currentChangeMessages = lecture.getChangeMessages();
                allAppointments.addAll(currentAppointments);
                allChangeMessages.addAll(currentChangeMessages);
            }
        }
    }

    @Test
    public void getCategoriesTest() {

        final WebResource webResource = client.resource(CALENDAR_SERVICE + "/categories");
        final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        checkResponse(response);

        final List<CategoryPO> categories = response.getEntity(new GenericType<List<CategoryPO>>() {

        });
        assertTrue(categories.containsAll(allCategories));
        assertTrue(allCategories.containsAll(categories));

    }

    @Test
    public void getCategoryTest() {
        for(final CategoryBO category : allCategories) {
            final WebResource webResource = client.resource(CALENDAR_SERVICE + "/category/" + category.getUuid());
            final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            checkResponse(response);

            final CategoryBO responseCat = response.getEntity(CategoryPO.class);
            assertEquals(category, responseCat);
        }

    }

    @Test
    public void getLecturesTest() {
        for(final CategoryBO category : allCategories) {
            final WebResource webResource = client.resource(CALENDAR_SERVICE + "/lectures/" + category.getUuid());
            final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            checkResponse(response);

            final List<LecturePO> lectures = response.getEntity(new GenericType<List<LecturePO>>() {

            });
            assertTrue(lectures.containsAll(category.getLectures()));
            assertTrue(category.getLectures().containsAll(lectures));
        }

    }

    @Test
    public void getAppointmentsTest() {
        for(final LectureBO lecture : allLectures) {
            final WebResource webResource = client.resource(CALENDAR_SERVICE + "/appointments/" + lecture.getUuid());
            final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            checkResponse(response);

            final List<AppointmentPO> appointments = response.getEntity(new GenericType<List<AppointmentPO>>() {

            });
            assertTrue(appointments.containsAll(lecture.getAppointments()));
            assertTrue(lecture.getAppointments().containsAll(appointments));
        }

    }

    @Test
    public void getAppointmentTest() {
        for(final AppointmentBO appointment : allAppointments) {
            final WebResource webResource = client.resource(CALENDAR_SERVICE + "/appointment/" + appointment.getUuid());
            final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            checkResponse(response);

            final AppointmentPO responseCat = response.getEntity(AppointmentPO.class);
            assertEquals(appointment, responseCat);
        }

    }

    @Test
    public void getChangemessagesTest() {
        for(final LectureBO lecture : allLectures) {
            final WebResource webResource = client.resource(CALENDAR_SERVICE + "/changeMessages/" + lecture.getUuid());
            final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            checkResponse(response);

            final List<ChangeMessagePO> changeMessages = response.getEntity(new GenericType<List<ChangeMessagePO>>() {

            });
            assertTrue(changeMessages.containsAll(lecture.getChangeMessages()));
            assertTrue(lecture.getChangeMessages().containsAll(changeMessages));
        }

    }

    @Test
    public void getChangemessageTest() {
        for(final ChangeMessageBO changeMessage : allChangeMessages) {
            final WebResource webResource = client.resource(CALENDAR_SERVICE + "/changeMessage/"
                            + changeMessage.getUuid());
            final ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

            checkResponse(response);

            final ChangeMessagePO responseMesg = response.getEntity(ChangeMessagePO.class);
            assertEquals(changeMessage, responseMesg);
        }
    }

}
