package de.minimum.hawapp.app.calendar.beans;

import java.util.Date;
import java.util.List;

public interface CategoryBO {

    public String getUuid();

    public String getName();

    public void setName(String name);

    public Date getLastModified();

    public List<LecturePO> getLectures();

}