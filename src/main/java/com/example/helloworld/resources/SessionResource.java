package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.Saying;
import com.example.helloworld.core.Session;
import com.example.helloworld.core.User;
import com.example.helloworld.db.SessionDAO;
import com.example.helloworld.db.UserDAO;

import com.google.common.base.Optional;
import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.jersey.api.NotFoundException;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;

@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionResource {
    private final UserDAO userDAO;
    private final SessionDAO sessionDAO;

    public SessionResource(UserDAO userDAO, SessionDAO sessionDAO) {
        this.userDAO = userDAO;
        this.sessionDAO = sessionDAO;
    }

    @POST
    @Path("/{username}/{password}")
    @UnitOfWork
    public Session login(@PathParam("username") String username, @PathParam("password") String password ) throws Exception {
        System.out.println("Login:: username - " + username + ", password:: " + password);
       /* User user = userDAO.findByCredentials(username, password);
        if (user == null) {
            throw new Exception("user not found");
        }*/

        User user = userDAO.findByCredentials(username, password);
        if (user == null) {
            throw new Exception("user not found");
        }
       // User u = user.get();

        Session session = new Session(username);
        session.setRole(user.getRole());

        sessionDAO.create(session);

        return session;
    }


    @POST
    @Path("/logout")
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean login(Session session) throws Exception {
        System.out.println("Login:: username - " + session.getIdentity() );

        boolean deleted = sessionDAO.deleteSession(session);
        return deleted;
    }
}
