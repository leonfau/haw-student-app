package de.minimum.hawapp.server.persistence.calendar;

// Generated 26.04.2013 13:35:12 by Hibernate Tools 3.4.0.CR1

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import de.minimum.hawapp.server.calendar.api.CategoryBO;

/**
 * CalendarCategory generated by hbm2java
 */
@Entity
@Table(name = "Calendar_Category", catalog = "haw_app")
public class CategoryPO implements CategoryBO, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String name;
    private Date lastModified;
    private Set<LecturePO> lectures = new HashSet<LecturePO>(0);

    public CategoryPO() {
    }

    public CategoryPO(final String uuid, final Date lastModified) {
        this.uuid = uuid;
        this.lastModified = lastModified;
    }

    public CategoryPO(final String uuid, final String name, final Date lastModified,
                    final Set<LecturePO> calendarLectures) {
        this.uuid = uuid;
        this.name = name;
        this.lastModified = lastModified;
        lectures = calendarLectures;
    }

    @Override
    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    @Override
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastModified", nullable = false, length = 19)
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(final Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @JsonIgnore
    public Set<LecturePO> getLectures() {
        return lectures;
    }

    // public Set<String> getLectureUuids(){
    // Set<String> set=new HashSet<String>();
    // for(LecturePO lecture:lectures){
    // set.add(lecture.getUuid());
    // }
    // return set;
    // }

    public void setLectures(final Set<LecturePO> calendarLectures) {
        lectures = calendarLectures;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoryPO other = (CategoryPO)obj;
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        }
        else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }
}
