package no.mesan.faghelg.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event implements Parcelable {

    private String title;
    private String hostNames;
    private String description;
    private String eventImageUrl;
    private Person responsible;
    private long start;
    private long end;

    public Event() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHostNames() {
        return hostNames;
    }

    public void setHostNames(String hostNames) {
        this.hostNames = hostNames;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventImageUrl() {
        return eventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        this.eventImageUrl = eventImageUrl;
    }

    public Person getResponsible() {
        return responsible;
    }

    public void setResponsible(Person responsible) {
        this.responsible = responsible;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public DateTime getStartTime() {
        return new DateTime(start*1000);
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public DateTime getEndTime() {
        return new DateTime(end*1000);
    }

    protected Event(Parcel in) {
        title = in.readString();
        hostNames = in.readString();
        description = in.readString();
        eventImageUrl = in.readString();
        responsible = (Person) in.readValue(Person.class.getClassLoader());
        start = in.readLong();
        end = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(hostNames);
        dest.writeString(description);
        dest.writeString(eventImageUrl);
        dest.writeValue(responsible);
        dest.writeLong(start);
        dest.writeLong(end);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
