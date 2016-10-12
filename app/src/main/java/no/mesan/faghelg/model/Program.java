package no.mesan.faghelg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {

    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEventsForDay(int day) {
        List<Event> eventsForDay = new ArrayList<>();
        for (Event event : events) {
            DateTime start = new DateTime(event.getStart()*1000);
            if (start.getDayOfWeek() == day) {
                eventsForDay.add(event);
            }
        }
        return eventsForDay;
    }
}
