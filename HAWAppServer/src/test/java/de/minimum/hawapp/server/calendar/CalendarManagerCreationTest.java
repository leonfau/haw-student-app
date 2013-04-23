package de.minimum.hawapp.server.calendar;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.calendar.api.SemesterBO;
import de.minimum.hawapp.server.calendar.intern.Dates;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.test.util.rules.AutoCleanUpRule;
@Ignore
public class CalendarManagerCreationTest {
	@Rule
	public static AutoCleanUpRule rule=new AutoCleanUpRule();
	
	public static CalendarManager calendarMgr=ManagerFactory.getManager(CalendarManager.class);
	
	
	private static SemesterBO newSemester;
	private static CategoryBO newCategory_1;
	private static CategoryBO newCategory_2;
	private static LectureBO newLecture_1;
	private static LectureBO newLecture_2;
	private static AppointmentBO appointment_1;
	private static AppointmentBO appointment_2;
	private static ChangeMessageBO changeMessage_1;
	private static ChangeMessageBO changeMessage_2;
	
	@Before
	public void setUp(){
		newSemester=calendarMgr.createSemesterBO(Dates.getDate("01.02.2012"), Dates.getDate("03.02.2012"));
		
		newCategory_1=calendarMgr.createCategoryBO(newSemester, "category_1");
		newCategory_2=calendarMgr.createCategoryBO(newSemester, "category_2");
		
		newLecture_1=calendarMgr.createLectureBO(newCategory_1, "lecture_1");
		newLecture_2=calendarMgr.createLectureBO(newCategory_1, "lecture_2");
		appointment_1=calendarMgr.createAppointment(newLecture_1, "appointment_1",Dates.getDateAndTime("01.02.2012","12:25") ,Dates.getDateAndTime("01.02.2012","13:25"));
		appointment_2=calendarMgr.createAppointment(newLecture_1, "appointment_2",Dates.getDateAndTime("01.02.2012","12:25") ,Dates.getDateAndTime("01.02.2012","13:25"));
		changeMessage_1=calendarMgr.createChangeMessage(newLecture_1, Dates.getDate("01.02.2012"), "reason_1", "what_1", "person_1");
		changeMessage_2=calendarMgr.createChangeMessage(newLecture_1, Dates.getDate("01.02.2012"), "reason_2", "what_2", "person_2");
	}

	@Test
	public void testSemesterCreation() {
		String uuid=newSemester.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(newSemester.getBegin(), Dates.getDate("01.02.2012"));
		assertEquals(newSemester.getEnd(), Dates.getDate("03.02.2012"));
	}
	
	@Test
	public void testCategoryCreation(){
		String uuid=newCategory_1.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(newCategory_1.getName(), "category_1");
		assertEquals(newCategory_1.getSemester(), newSemester);
		
		uuid=newCategory_2.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(newCategory_2.getName(), "category_2");
		assertEquals(newCategory_2.getSemester(), newSemester);
		
		Set<CategoryBO> categories=calendarMgr.getSemesterBO(newSemester.getUuid()).getCategorieBOs();
		assertTrue(categories.size()==2);
		assertTrue(categories.contains(newCategory_1));
		assertTrue(categories.contains(newCategory_2));
	}
	@Test
	public void testLectureCreation(){
		String uuid=newLecture_1.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(newLecture_1.getName(), "lecture_1");
		assertEquals(newLecture_1.getCategory(), newCategory_1);
		
		uuid=newLecture_2.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(newLecture_2.getName(), "lecture_2");
		assertEquals(newLecture_2.getCategory(), newCategory_1);
		
		Set<LectureBO> categories=calendarMgr.getLecturesFromCategory(newCategory_1.getUuid());
		assertTrue(categories.size()==2);
		assertTrue(categories.contains(newLecture_2));
		assertTrue(categories.contains(newLecture_1));
	}
	@Test
	public void testAppointmentCreation(){
		String uuid=appointment_1.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(appointment_1.getName(), "appointment_1");
		assertEquals(appointment_1.getBegin().getTime(),Dates.getDateAndTime("01.02.2012","12:25").getTime());
		assertEquals(appointment_1.getEnd().getTime(), Dates.getDateAndTime("01.02.2012","13:25").getTime() );
		assertEquals(appointment_1.getLecture(), newLecture_1);
	
		Set<AppointmentBO> categories=calendarMgr.getAppointmentsFromLecture(newLecture_1.getUuid());
		assertTrue(categories.size()==2);
		assertTrue(categories.contains(appointment_1));
		assertTrue(categories.contains(appointment_2));
	}
	@Test
	public void testChangeMessageCreation(){
		String uuid=changeMessage_1.getUuid();
		assertTrue(uuid.length()>0);
		assertEquals(changeMessage_1.getChangeat().getTime(),Dates.getDate("01.02.2012").getTime() );
		assertEquals(changeMessage_1.getReason(), "reason_1");
		assertEquals(changeMessage_1.getWhat(), "what_1");
		assertEquals(changeMessage_1.getPerson(), "person_1");
		assertEquals(changeMessage_1.getLecture(), newLecture_1);
	
		
		Set<ChangeMessageBO> categories=calendarMgr.getChangeMessageFromLecture(newLecture_1.getUuid());
		assertTrue(categories.size()==2);
		assertTrue(categories.contains(changeMessage_1));
		assertTrue(categories.contains(changeMessage_2));
	}

}
