package no.mesan.faghelg.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DailyProgram implements Parcelable {

    private String day;
    private List<Event> events;

    public DailyProgram() {
    }

    public DailyProgram(String day, List<Event> events) {
        this.day = day;
        this.events = events;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * PARCELABLE
     **/

    protected DailyProgram(Parcel in) {
        day = in.readString();
        if (in.readByte() == 0x01) {
            events = new ArrayList<Event>();
            in.readList(events, Event.class.getClassLoader());
        } else {
            events = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        if (events == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(events);
        }
    }

    public static final Parcelable.Creator<DailyProgram> CREATOR = new Parcelable.Creator<DailyProgram>() {
        @Override
        public DailyProgram createFromParcel(Parcel in) {
            return new DailyProgram(in);
        }

        @Override
        public DailyProgram[] newArray(int size) {
            return new DailyProgram[size];
        }
    };
}
