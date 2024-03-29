package de.minimum.hawapp.server.mensa;

import java.util.UUID;


public class MealImpl implements Meal {
	
	private UUID id;
    private String description;
    private double studentPrice;
    private double othersPrice;
    private Rating rating;

    private MealImpl(String description, double studentPrice, double othersPrice) {
    	this.id = UUID.randomUUID();
        this.description = description;
        this.studentPrice = studentPrice;
        this.othersPrice = othersPrice;
        this.rating = RatingImpl.Rating();
    }

    public static MealImpl Meal(String description, double studentPrice, double othersPrice) {
        return new MealImpl(description, studentPrice, othersPrice);
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public double getStudentPrice() {
        return this.studentPrice;
    }

    @Override
    public double getOthersPrice() {
        return this.othersPrice;
    }

    @Override
    public String toString() {
        return this.description + " " + this.studentPrice + " E / " + this.othersPrice + " E";
    }

    @Override
    public Rating getRating() {
        return this.rating;
    }
    
    @Override
    public UUID getID(){
    	return id;
    }

    @Override
    public boolean equals(Object obj){
    	if (this == obj){
    		return true;
    	}
    	if(obj == null){
    		return false;
    	}
    	if(!(obj instanceof Meal)){
    		return false;
    	}
    	Meal other = (Meal) obj;
    	return (this.getDescription().equals(other.getDescription())) && 
    			(this.getStudentPrice() == other.getStudentPrice()) && 
    			(this.getOthersPrice() == other.getOthersPrice());
    }
}
