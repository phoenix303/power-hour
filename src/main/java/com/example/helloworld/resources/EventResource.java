package com.example.helloworld.resources;

import com.example.helloworld.core.Event;
import com.example.helloworld.db.EventDAO;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.jersey.api.NotFoundException;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;

@Path("/event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {

    private final EventDAO eventDAO;

    public EventResource(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @POST
    @UnitOfWork
    public Event createEvent(Event event) {
        return eventDAO.create(event);
    }

    @GET
    @UnitOfWork
    public List<Event> listEvent() {
        return eventDAO.findAll();
    }


    @GET
    @Path("/{eventId}")
    @UnitOfWork
    public Event getEvent(@PathParam("eventId") LongParam eventId) {
        return findSafely(eventId.get());
    }

    private Event findSafely(long eventId) {
        final Optional<Event> event = eventDAO.findById(eventId);
        if (!event.isPresent()) {
            throw new NotFoundException("No such eventId.");
        }
        return event.get();
    }
}
