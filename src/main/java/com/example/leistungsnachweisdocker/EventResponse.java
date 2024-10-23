package com.example.leistungsnachweisdocker;

import lombok.Data;

import java.util.List;

/**
 * The EventResponse class serves as a data model for holding a list of Event objects
 */
@Data
public class EventResponse {
    private List<Event> events;

    // Getter and setter
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
