package de.minimum.hawapp.server.persistence.blackboard;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Offer;

@Entity
@Table(name = "Blackboard_Category", catalog = "haw_app")
public class CategoryEntity implements Category {

    /**
     * 
     */
    private static final long serialVersionUID = -8261124234228049510L;
    private String name;
    private List<Offer> allOffers = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setAllOffers(List<Offer> offers) {
        this.allOffers = offers;
    }

    @Override
    public void addOffer(Offer offer) {
        if (!this.allOffers.contains(offer))
            this.allOffers.add(offer);
    }

    @Override
    public boolean removeOffer(Offer offer) {
        return this.allOffers.remove(offer);
    }

    @Override
    public boolean removeOffer(long offerId) {
        for(Offer offer : this.allOffers) {
            if (offer.getId() == offerId) {
                removeOffer(offer);
                return true;
            }
        }
        return false;
    }

    @Id
    @Column(name = "name", unique = true, nullable = false)
    @Override
    public String getName() {
        return this.name;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category", targetEntity = OfferEntity.class)
    @Override
    public List<Offer> getAllOffers() {
        return this.allOffers;
    }

    @Override
    public boolean equalKey(Category o) {
        return this.name.equals(o.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CategoryEntity other = (CategoryEntity)obj;
        if (this.name == null) {
            if (other.name != null)
                return false;
        }
        else if (!this.name.equals(other.name))
            return false;
        if (this.allOffers == null) {
            if (other.allOffers != null)
                return false;
        }
        else if (!this.allOffers.equals(other.allOffers))
            return false;
        return true;
    }
}
