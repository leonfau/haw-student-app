package de.minimum.hawapp.server.persistence.blackboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Offer;

public class CategoryEntity implements Category {

    /**
     * 
     */
    private static final long serialVersionUID = -8261124234228049510L;
    private String name;
    private Map<Long, Offer> offers = new HashMap<Long, Offer>();

    public void setName(String name) {
        this.name = name;
    }

    public void setAllOffers(List<Offer> offers) {
        offers.clear();
        for(Offer offer : offers) {
            addOffer(offer);
        }
    }

    @Override
    public void addOffer(Offer offer) {
        this.offers.put(offer.getId(), offer);
    }

    @Override
    public boolean removeOffer(Offer offer) {
        return removeOffer(offer.getId());
    }

    @Override
    public boolean removeOffer(long offerId) {
        return this.offers.remove(offerId) != null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Offer> getAllOffers() {
        return new ArrayList<Offer>(this.offers.values());
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
        if (this.offers == null) {
            if (other.offers != null)
                return false;
        }
        else if (!this.offers.equals(other.offers))
            return false;
        return true;
    }
}
