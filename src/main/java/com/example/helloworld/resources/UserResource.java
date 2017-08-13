package com.example.helloworld.resources;

import com.example.helloworld.core.Event;
import com.example.helloworld.core.User;
import com.example.helloworld.db.EventDAO;
import com.example.helloworld.db.UserDAO;

import com.google.common.base.Optional;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.NotFoundException;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;
    private final EventDAO eventDAO;

    public UserResource(UserDAO userDAO, EventDAO eventDAO) {
        this.userDAO = userDAO;
        this.eventDAO = eventDAO;
    }

    @POST
    @UnitOfWork
    public User createUser(User user) {
        return userDAO.create(user);
    }

    @GET
    @UnitOfWork
    public List<User> listUser(@QueryParam("username") String username) {
        return userDAO.findAll(username);
    }


    @GET
    @Path("/{userId}")
    @UnitOfWork
    public User getUser(@PathParam("userId") LongParam userId) {
        return findSafely(userId.get());
    }

    private User findSafely(long userId) {
        final Optional<User> user = userDAO.findById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException("No such user.");
        }
        return user.get();
    }

    @GET
    @Path("/{userId}/events")
    @UnitOfWork
    public List<Event> getEventsForUser(@PathParam("userId") LongParam userId) {
        return this.eventDAO.findAll(userId.get());
    }
}




