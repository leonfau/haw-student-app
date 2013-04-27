package de.minimum.hawapp.server.calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CalendarParseManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;

public class CalendarParserTest {
    private static CalendarManager calendarManager = ManagerFactory.getManager(CalendarManager.class);
    private static CalendarParseManager cl = ManagerFactory.getManager(CalendarParseManager.class);
    @ClassRule
    public static AutoCleanUpRule rule = new AutoCleanUpRule();

    private static Set<? extends CategoryBO> allCategories;
    private static Set<LectureBO> allLectures;
    private static Set<AppointmentBO> allAppointments;
    private static Set<ChangeMessageBO> allChangeMessages;

    @BeforeClass
    public static void test() throws IOException {
        calendarManager.deleteAllCalendarDataFromDB();
        cl.parseFromFileToDB("./src/test/java/de/minimum/hawapp/server/calendar/Sem_2012.txt", "Cp1250");

        allCategories = calendarManager.getAllCategories();

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
    public void noDuplicateAppointments() {
        for(final AppointmentBO app : allAppointments) {
            for(final AppointmentBO vergleich : allAppointments) {
                if (app.getName().equals(vergleich.getName()) && !app.getUuid().equals(vergleich.getUuid())) {
                    assertFalse(app.getBegin().equals(vergleich.getBegin()) && app.getEnd().equals(vergleich.getEnd()));
                }
            }
        }
    }

    @Test
    public void allAppointmentinTheRightLecture() {
        for(final LectureBO lecture : allLectures) {
            final String lectureName = lecture.getName();
            for(final AppointmentBO appointment : lecture.getAppointments()) {
                assertEquals(lectureName, appointment.getName());
            }
        }
    }

    @Test
    public void allLecturesInTheRightCategory() {
        for(final CategoryBO category : allCategories) {
            final String categoryName = category.getName();
            for(final LectureBO lecture : category.getLectures()) {
                final String lectureName = lecture.getName();
                assertTrue(lectureName.startsWith(categoryName));
            }
        }
    }

    @Test
    public void allObjectsArePresentInTheRightCardinality() {
        assertEquals(25, allCategories.size());
        assertEquals(11, allLectures.size());
        assertEquals(11, allChangeMessages.size());
        assertEquals(118, allAppointments.size());
    }

}
