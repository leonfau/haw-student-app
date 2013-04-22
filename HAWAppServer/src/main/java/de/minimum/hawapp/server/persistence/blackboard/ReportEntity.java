package de.minimum.hawapp.server.persistence.blackboard;

import de.minimum.hawapp.server.blackboard.api.Offer;
import de.minimum.hawapp.server.blackboard.api.Report;

public class ReportEntity implements Report {

    /**
     * 
     */
    private static final long serialVersionUID = -5167919085435902773L;
    private long id;
    private String reason;
    private Offer offer;

    public void setId(long id) {
        this.id = id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int)(this.id ^ (this.id >>> 32));
        result = prime * result + ((this.offer == null) ? 0 : this.offer.hashCode());
        result = prime * result + ((this.reason == null) ? 0 : this.reason.hashCode());
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
        ReportEntity other = (ReportEntity)obj;
        if (this.id != other.id)
            return false;
        if (this.offer == null) {
            if (other.offer != null)
                return false;
        }
        else if (!this.offer.equals(other.offer))
            return false;
        if (this.reason == null) {
            if (other.reason != null)
                return false;
        }
        else if (!this.reason.equals(other.reason))
            return false;
        return true;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public Offer getOffer() {
        return this.offer;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public boolean equalKey(Report o) {
        return this.id == o.getId();
    }
}
