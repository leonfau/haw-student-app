package de.minimum.hawapp.server.calendar.intern;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import de.minimum.hawapp.server.calendar.api.AppointmentBO;
import de.minimum.hawapp.server.calendar.api.CalendarManager;
import de.minimum.hawapp.server.calendar.api.CategoryBO;
import de.minimum.hawapp.server.calendar.api.ChangeMessageBO;
import de.minimum.hawapp.server.calendar.api.LectureBO;
import de.minimum.hawapp.server.context.ManagerFactory;
import de.minimum.hawapp.server.persistence.HibernateSessionMgr;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;
import de.minimum.hawapp.server.persistence.calendar.CategoryPO;
import de.minimum.hawapp.server.persistence.calendar.ChangeMessagePO;
import de.minimum.hawapp.server.persistence.calendar.LecturePO;

public class CalendarManagerImpl implements CalendarManager {
    private static HibernateSessionMgr hibernateSessionMgr = ManagerFactory.getManager(HibernateSessionMgr.class);

    @Override
    public CategoryBO createCategoryBO(String categoryName) {
        CategoryPO category = new CategoryPO();
        category.setName(categoryName);
        return createCategoryBO(category);
    }

    @Override
    public CategoryBO createCategoryBO(CategoryBO transientCategoryBO) {
        CategoryPO category = (CategoryPO)transientCategoryBO;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        category.setUuid(uuid);
        category.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(category);
        transaction.commit();
        return getCategoryBO(uuid);
    }

    @Override
    public LectureBO createLectureBO(CategoryBO category, String lectureName) {
        LecturePO lecture = new LecturePO();
        lecture.setName(lectureName);
        lecture.setCategory((CategoryPO)category);
        return createLectureBO(lecture);
    }

    @Override
    public LectureBO createLectureBO(LectureBO transientLecture) {
        LecturePO lecture = (LecturePO)transientLecture;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        lecture.setUuid(uuid);
        lecture.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(lecture);
        transaction.commit();
        return getLectureBO(uuid);
    }

    @Override
    public AppointmentBO createAppointment(LectureBO lecture, String appoinmentName, Date begin, Date end) {
        AppointmentPO appointment = new AppointmentPO();
        appointment.setLecture((LecturePO)lecture);
        appointment.setBegin(begin);
        appointment.setEnd(end);
        appointment.setName(appoinmentName);
        return createAppointment(appointment);
    }

    @Override
    public AppointmentBO createAppointment(AppointmentBO transientAppointment) {
        AppointmentPO appointment = (AppointmentPO)transientAppointment;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        appointment.setUuid(uuid);
        appointment.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(appointment);
        transaction.commit();
        return getAppointment(uuid);
    }

    @Override
    public ChangeMessageBO createChangeMessage(LectureBO lecture, Date changeAt, String reason, String what,
                    String fromPerson) {
        ChangeMessagePO changemsg = new ChangeMessagePO();
        changemsg.setChangeat(changeAt);
        changemsg.setLecture((LecturePO)lecture);
        changemsg.setReason(reason);
        changemsg.setWhat(what);
        changemsg.setPerson(fromPerson);
        return createChangeMessage(changemsg);
    }

    @Override
    public ChangeMessageBO createChangeMessage(ChangeMessageBO transientMessage) {
        ChangeMessagePO changemessage = (ChangeMessagePO)transientMessage;
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        String uuid = UUID.randomUUID().toString();
        changemessage.setUuid(uuid);
        changemessage.setLastModified(new Date(System.currentTimeMillis()));
        transaction.begin();
        session.persist(changemessage);
        transaction.commit();
        return getChangeMessage(uuid);
    }

    @Override
	public Set<? extends CategoryBO> getAllCategories() {
    	 Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
    	 Transaction transaction = session.getTransaction();
    	 transaction.begin();
    	 @SuppressWarnings("unchecked")
		Set<CategoryPO> set = new HashSet<CategoryPO>(session.createCriteria(CategoryPO.class).list());
    	 transaction.commit();
    	 return set;
	}
    @Override
    public CategoryBO getCategoryBO(String uuid) {
        return (CategoryBO)getPOObject(CategoryPO.class, uuid);
    }

    @Override
    public LectureBO getLectureBO(String uuid) {
        return (LectureBO)getPOObject(LecturePO.class, uuid);
    }

    @Override
    public ChangeMessageBO getChangeMessage(String uuid) {
        return (ChangeMessageBO)getPOObject(ChangeMessagePO.class, uuid);
    }

    @Override
    public AppointmentBO getAppointment(String uuid) {
        return (AppointmentBO)getPOObject(AppointmentPO.class, uuid);
    }

   

    @Override
    public Set<? extends AppointmentBO> getAppointmentsFromLecture(String LectureUuid) {
        return getLectureBO(LectureUuid).getAppointments();
    }

    @Override
    public Set<? extends LectureBO> getLecturesFromCategory(String categoryUuid) {
        return getCategoryBO(categoryUuid).getLectures();
    }

    @Override
    public Set<? extends ChangeMessageBO> getChangeMessageFromLecture(String LectureUuid) {
        return getLectureBO(LectureUuid).getChangeMessages();
    }

    @Override
    public void modify(CategoryBO category) {
    	CategoryPO categoryPO=(CategoryPO)category;
    	categoryPO.setLastModified(new Date(System.currentTimeMillis()));
    	modifyObject(categoryPO);
    }

    @Override
    public void modify(LectureBO lecture) {
    	LecturePO lecturePO=(LecturePO)lecture;
    	lecturePO.setLastModified(new Date(System.currentTimeMillis()));
    	modifyObject(lecturePO);

    }

    @Override
    public void modify(AppointmentBO appointment) {
    	AppointmentPO appointmentPO=(AppointmentPO)appointment;
    	appointmentPO.setLastModified(new Date(System.currentTimeMillis()));
        modifyObject(appointmentPO);
    }

    @Override
    public void modify(ChangeMessageBO changemessage) {
    	ChangeMessagePO changeMessagePO=(ChangeMessagePO)changemessage;
    	changeMessagePO.setLastModified(new Date(System.currentTimeMillis()));
        modifyObject(changeMessagePO);

    }


    @Override
    public void delete(CategoryBO category) {
        deleteObject(category);
    }

    @Override
    public void delete(LectureBO lecture) {
        deleteObject(lecture);
    }

    @Override
    public void delete(ChangeMessageBO changemessage) {
        deleteObject(changemessage);
    }

    @Override
    public void delete(AppointmentBO appointment) {
        deleteObject(appointment);
    }

    private void modifyObject(Object object) {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(object);
        transaction.commit();
    }

    private void deleteObject(Object object) {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.delete(object);
        transaction.commit();
    }

    private Object getPOObject(Class<?> clazz, String uuid) {
        Session session = CalendarManagerImpl.hibernateSessionMgr.getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        Object object = session.get(clazz, uuid);
        transaction.commit();
        return object;
    }

	

}
