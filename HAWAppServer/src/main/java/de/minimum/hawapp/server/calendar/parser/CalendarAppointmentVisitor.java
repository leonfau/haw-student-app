package de.minimum.hawapp.server.calendar.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.minimum.hawapp.server.calendar.parser.CalendarParser.TerminContext;
import de.minimum.hawapp.server.persistence.calendar.AppointmentPO;

public class CalendarAppointmentVisitor extends CalendarBaseVisitor<CalendarAppointmentVisitor> {
	public CalendarAppointmentVisitor(){
		super();
	}
	public List<AppointmentPO> getAppointments() {
		return appointments;
	}
	List<AppointmentPO> appointments=new ArrayList<AppointmentPO>();
	
	@Override
	public CalendarAppointmentVisitor visitTermin(TerminContext ctx) {
	     try{
	     for(Integer week:ctx.wochen){
	        Calendar cal = Calendar.getInstance(Locale.GERMANY);
	        cal.setTimeInMillis(0);
	        cal.setWeekDate(ctx.year,week, ctx.tag().weekday);
	        Date date=cal.getTime();
	        Date begin=new Date(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMANY).parse(ctx.anfang().getText()).getTime()+date.getTime());
	        Date end=new Date(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.GERMANY).parse(ctx.ende().getText()).getTime()+date.getTime());
	        AppointmentPO appointment=new AppointmentPO();
	        appointment.setName(ctx.name().getText());
	        appointment.setLocation(ctx.raum().getText());
	        appointment.setBegin(begin);
	        appointment.setEnd(end);
	        appointment.setDetails(ctx.dozent().getText());
	        appointment.setUuid(UUID.randomUUID().toString());
	        appointments.add(appointment);
	        }
	     }catch(ParseException e){throw new RuntimeException(e);}
	     
		return super.visitTermin(ctx);
	}

}
