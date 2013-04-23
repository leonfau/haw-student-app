package de.minimum.hawapp.test.rest.entities.mensaservice;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MealEntity {
    private String description;
    private double studentPrice;
    private double othersPrice;
    private RatingEntity rating;
    private UUID id;

    public String getDescription() {
        return this.description;
    }

    public double getStudentPrice() {
        return this.studentPrice;
    }

    public double getOthersPrice() {
        return this.othersPrice;
    }

    public RatingEntity getRating() {
        return this.rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStudentPrice(double studentPrice) {
        this.studentPrice = studentPrice;
    }

    public void setOthersPrice(double othersPrice) {
        this.othersPrice = othersPrice;
    }

    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

}
