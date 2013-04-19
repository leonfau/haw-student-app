package de.minimum.hawapp.test.rest.entities.mensaservice;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DayplanEntity {
    private String day;
    private List<MealEntity> meals;

    public String getDay() {
        return this.day;
    }

    public List<MealEntity> getMeals() {
        return this.meals;
    }

    public void setMeals(List<MealEntity> meals) {
        this.meals = meals;
    }

    public void setDay(String day) {
        this.day = day;
    }

}
