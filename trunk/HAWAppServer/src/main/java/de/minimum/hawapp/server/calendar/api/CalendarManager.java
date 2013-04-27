package de.minimum.hawapp.server.calendar.api;

import java.util.Date;
import java.util.Set;





public interface CalendarManager {
    
    public CategoryBO getCategoryBO(String uuid);
    public Set<? extends CategoryBO> getAllCategories();
    public CategoryBO createCategoryBO(String categoryName);
    public CategoryBO createCategoryBO(CategoryBO transientCategoryBO);
    public void delete(CategoryBO category);
    public void modify(CategoryBO category);
    
    public LectureBO getLectureBO(String uuid);
    public Set<? extends LectureBO> getLecturesFromCategory(String categoryUuid);
    public LectureBO createLectureBO(CategoryBO category, String lectureName);
    public LectureBO createLectureBO(LectureBO transientLecture);
    public void delete(LectureBO lecture);
    public void modify(LectureBO lecture);
//    public Date lectureLastModified(String uuid);
    
    public ChangeMessageBO getChangeMessage(String uuid);
    public Set<? extends ChangeMessageBO> getChangeMessageFromLecture(String LectureUuid);
    public ChangeMessageBO createChangeMessage(LectureBO lecture, Date changeAt, String reason, String what, String fromPerson);
    public ChangeMessageBO createChangeMessage(ChangeMessageBO transientMessage);
    public void delete(ChangeMessageBO changemessage);
    public void modify(ChangeMessageBO changemessage);
    
    public AppointmentBO getAppointment( String uuid);
    public Set<? extends AppointmentBO> getAppointmentsFromLecture(String LectureUuid);
    public AppointmentBO createAppointment(LectureBO lecture, String appoinmentName, Date begin, Date end);
    public AppointmentBO createAppointment(AppointmentBO transientAppointment);
    public void delete(AppointmentBO appointment);
    public void modify(AppointmentBO appointment);
//    public Date appointmentLastModified(String uuid);
    
}
