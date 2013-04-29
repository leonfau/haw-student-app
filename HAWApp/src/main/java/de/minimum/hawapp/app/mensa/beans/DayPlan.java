package de.minimum.hawapp.app.mensa.beans;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonDeserialize(as=DayPlanBeanImpl.class)
public interface DayPlan {

	/**
	 * 
	 * @return Tag des Essensplan
	 */
	public String getDay();
	
	/**
	 * 
	 * @return Liste der Speisen für einen Tag
	 */
	public List<Meal> getMeals();

}
