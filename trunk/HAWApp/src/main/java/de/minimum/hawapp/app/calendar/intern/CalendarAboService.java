package de.minimum.hawapp.app.calendar.intern;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import de.minimum.hawapp.app.calendar.beans.Lecture;

public class CalendarAboService implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6463251438511431991L;
    private final static String CALENDAR_ABO_FILENAME = "calAboPersistenceData.per";

    private final List<LectureSubscribtion> subscribtions = new ArrayList<LectureSubscribtion>();
    private transient Context context;

    CalendarAboService(final Context context) {
        this.context = context;
    }

    public void subscribeLecture(final Lecture lecture) {
        final LectureSubscribtion lectureSubscription = new LectureSubscribtion(lecture);
        subscribtions.add(lectureSubscription);
    }

    public void unsubscribeLecture(final String lectureUUID) {
        final Iterator<LectureSubscribtion> it = subscribtions.iterator();
        while(it.hasNext()) {
            final LectureSubscribtion actualSubscription = it.next();
            if (actualSubscription.getLectureUUID().equals(lectureUUID)) {
                it.remove();
            }
        }
    }

    public List<LectureSubscribtion> getSubscribtedLectures() {
        return subscribtions;
    }

    public boolean isSubscribted(final String lectureUUID) {
        for(final LectureSubscribtion sub : subscribtions) {
            if (sub.getLectureUUID().equals(lectureUUID)) {
                return true;
            }
        }
        return false;
    }

    static CalendarAboService getSavedCalendarAboService(final Context context) {
        CalendarAboService aboService = null;
        FileInputStream f_in = null;
        try {
            // Read from disk using FileInputStream
            f_in = context.openFileInput(CALENDAR_ABO_FILENAME);

            final ObjectInputStream obj_in = new ObjectInputStream(f_in);

            // Read an object

            final Object obj = obj_in.readObject();

            if (obj instanceof CalendarAboService) {
                // Cast object to a Vector
                aboService = (CalendarAboService)obj;
                aboService.context = context;

            }
        }
        catch(final Throwable e) {
            e.printStackTrace();
        }
        finally {
            try {
                f_in.close();
            }
            catch(final Throwable e) {
                // nothing to catch
            }
        }
        return aboService;
    }

    public void persist() throws IOException {
        // Write to disk with FileOutputStream
        final FileOutputStream f_out = context.openFileOutput(CALENDAR_ABO_FILENAME, Context.MODE_PRIVATE);

        // Write object with ObjectOutputStream
        final ObjectOutputStream obj_out = new ObjectOutputStream(f_out);

        // Write object out to disk
        obj_out.writeObject(this);
        obj_out.flush();
        obj_out.close();
    }
}
