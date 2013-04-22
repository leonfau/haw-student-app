package de.minimum.hawapp.test.rest.entities.blackboardservice;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryEntity {
    private String name;
    private List<OfferEntity> offers;

    public void setName(String name) {
        this.name = name;
    }

    public void setAllOffers(List<OfferEntity> offers) {
        this.offers = offers;
    }

    public String getName() {
        return this.name;
    }

    public List<OfferEntity> getAllOffers() {
        return this.offers;
    }
}
