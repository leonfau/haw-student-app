package de.minimum.hawapp.app.blackboard.beans;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.minimum.hawapp.app.blackboard.api.Category;
import de.minimum.hawapp.app.blackboard.api.Offer;

public class CategoryBean implements Category {
    private String name;
    private List<Offer> allOffers;
    private List<Offer> visible;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Offer> getAllOffers() {
        return this.visible;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllOffers(OfferBean[] allOffers) {
        this.allOffers = Lists.newArrayList((Offer[])allOffers);
        this.visible = new ArrayList<Offer>(this.allOffers);
    }

    @Override
    public void ignoreOffers(List<Long> toIgnore) {
        List<Offer> toRemove = new ArrayList<Offer>();
        for(Offer o : this.visible) {
            if (toIgnore.contains(o.getId()))
                toRemove.add(o);
        }
        this.visible.removeAll(toRemove);
    }

    @Override
    public void unignoreOffer(long offerId) {
        for(Offer o : this.allOffers) {
            if (o.getId() == offerId) {
                if (!this.visible.contains(o))
                    this.visible.add(o);
                break;
            }
        }
    }

}
