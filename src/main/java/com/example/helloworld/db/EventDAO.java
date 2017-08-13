package com.example.helloworld.db;

import com.google.common.base.Optional;

import com.example.helloworld.core.Event;
import org.hibernate.SessionFactory;
import java.util.List;
import io.dropwizard.hibernate.AbstractDAO;

public class EventDAO extends AbstractDAO<Event> {
    public EventDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Event> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Event create(Event event) {
        return persist(event);
    }

    public List<Event> findAll() {
        return list(namedQuery("com.example.helloworld.core.Event.findAll"));
    }
}

