package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.Saying;
import com.example.helloworld.core.Template;
import com.example.helloworld.core.User;
import com.example.helloworld.db.UserDAO;

import com.google.common.base.Optional;

import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.jersey.api.NotFoundException;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.errors.ErrorMessage;
import io.dropwizard.jersey.params.IntParam;
import io.dropwizard.jersey.params.LongParam;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @POST
    @UnitOfWork
    public User createUser(User user) {
        return userDAO.create(user);
    }

    @GET
    @UnitOfWork
    public List<User> listUser() {
        return userDAO.findAll();
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
    @UnitOfWork
    public Response searchByUsername(@QueryParam("username") String username){

        if (username == null || !username.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage("No username entered")).build();
        }

        List<User> list = this.userDAO.searchByUsername(username);

        if (list.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new ErrorMessage("No results for username found")).build();
        }

        List<User> listCopy = new ArrayList<>();

        for(User user : list) {
            User userCopy = new User(user);
            userCopy.setPassword(null);
            listCopy.add(userCopy);
        }

        return Response.ok(listCopy).build();
    }


}




