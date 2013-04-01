package de.minimum.hawapp.server.calendar.api;

import java.util.*;
/*
 * Under Construction!
 */
public interface Semester {

	String getDescription();
 
	java.util.Date getBegin();

	List<de.minimum.hawapp.server.calendar.api.Category> getCategories();
}
