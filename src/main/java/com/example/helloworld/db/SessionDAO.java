package com.example.helloworld.db;

import com.example.helloworld.core.Session;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class SessionDAO extends AbstractDAO<Session> {
    public SessionDAO(SessionFactory factory) {
        super(factory);
    }

    public Session create(Session session) {
        return persist(session);
    }


    public boolean deleteSession(Session session) {
        currentSession().delete(session);
        //System.out.println("User id: " + user.getId());
        return true;
    }


}