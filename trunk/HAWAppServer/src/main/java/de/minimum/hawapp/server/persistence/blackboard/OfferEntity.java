package de.minimum.hawapp.server.persistence.blackboard;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.minimum.hawapp.server.blackboard.api.Category;
import de.minimum.hawapp.server.blackboard.api.Offer;

//TODO kommt die DB mit der rekursiven Strunktur zurecht: Offer -> Category -> Offer -> usw.
@Entity
@Table(name = "blackboard_offer", catalog = "haw_app")
public class OfferEntity implements Offer {

    /**
     * 
     */
    private static final long serialVersionUID = 3580355548763084727L;
    private long id = -1;
    private String header;
    private String description;
    private String contact;
    private Date dateOfCreation;
    private Long imageId = null;
    private String deletionKey;
    private Category category;

    public void setId(long id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        // dateOfCreation.setHours(0);
        // dateOfCreation.setMinutes(0);
        // dateOfCreation.setSeconds(0);// TODO ok so?
        this.dateOfCreation = dateOfCreation;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public void setDeletionKey(String deletionKey) {
        this.deletionKey = deletionKey;
    }

    @Column(name = "deletionKey")
    @Override
    public String getDeletionKey() {
        return this.deletionKey;
    }

    public void setCategoryName(String categoryName) {

    }

    public void setCategory(Category category) {
        if (this.category != null)
            this.category.removeOffer(this);
        this.category = category;
        // category.addOffer(this);
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public long getId() {
        return this.id;
    }

    @Column(name = "header")
    @Override
    public String getHeader() {
        return this.header;
    }

    @Column(name = "description")
    @Override
    public String getDescription() {
        return this.description;
    }

    @Column(name = "contact")
    @Override
    public String getContact() {
        return this.contact;
    }

    @Column(name = "dateOfCreation")
    @Override
    public Date getDateOfCreation() {
        return this.dateOfCreation;
    }

    @Column(name = "imageId")
    @Override
    public Long getImageId() {
        return this.imageId;
    }

    @Override
    public String getCategoryName() {
        return this.category.getName();
    }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = CategoryEntity.class)
    @JoinColumn(name = "categoryName", nullable = false, insertable = false, updatable = false)
    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public boolean equalKey(Offer o) {
        return this.id == o.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.category == null) ? 0 : this.category.hashCode());
        result = prime * result + ((this.contact == null) ? 0 : this.contact.hashCode());
        result = prime * result + ((this.dateOfCreation == null) ? 0 : this.dateOfCreation.hashCode());
        result = prime * result + ((this.deletionKey == null) ? 0 : this.deletionKey.hashCode());
        result = prime * result + ((this.description == null) ? 0 : this.description.hashCode());
        result = prime * result + ((this.header == null) ? 0 : this.header.hashCode());
        result = prime * result + (int)(this.id ^ (this.id >>> 32));
        if (this.imageId != null)
            result = prime * result + (int)(this.imageId ^ (this.imageId >>> 32));
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
        OfferEntity other = (OfferEntity)obj;
        if (this.category == null) {
            if (other.category != null)
                return false;
        }
        else if (!this.category.getName().equals(other.category.getName()))
            return false;
        if (this.contact == null) {
            if (other.contact != null)
                return false;
        }
        else if (!this.contact.equals(other.contact))
            return false;
        if (this.dateOfCreation == null) {
            if (other.dateOfCreation != null)
                return false;
        }
        Calendar thisCal = Calendar.getInstance();
        thisCal.setTime(this.dateOfCreation);
        Calendar otherCal = Calendar.getInstance();
        otherCal.setTime(this.dateOfCreation);
        if (!(thisCal.get(Calendar.DAY_OF_YEAR) == otherCal.get(Calendar.DAY_OF_YEAR) && (thisCal.get(Calendar.YEAR) == otherCal
                        .get(Calendar.YEAR))))// Weil die Uhrzeit in der DB
                                              // nicht gespeichert wird
            return false;
        if (this.deletionKey == null) {
            if (other.deletionKey != null)
                return false;
        }
        else if (!this.deletionKey.equals(other.deletionKey))
            return false;
        if (this.description == null) {
            if (other.description != null)
                return false;
        }
        else if (!this.description.equals(other.description))
            return false;
        if (this.header == null) {
            if (other.header != null)
                return false;
        }
        else if (!this.header.equals(other.header))
            return false;
        if (this.id != other.id)
            return false;
        if (this.imageId != other.imageId)
            return false;
        return true;
    }
}
