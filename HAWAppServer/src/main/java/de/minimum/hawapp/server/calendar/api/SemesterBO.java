package de.minimum.hawapp.server.calendar.api;

import java.util.Date;
import java.util.Set;

public interface SemesterBO {

	public String getUuid();

	public Date getBegin();

	public void setBegin(Date begin);

	public Date getEnd();

	public void setEnd(Date end);

	public Date getLastModified();

	public Set<CategoryBO> getCategorieBOs();
	
	public CategoryBO createCategory(String name);

}