package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.Saying;
import com.example.helloworld.core.Template;
import com.example.helloworld.core.School;
import com.example.helloworld.db.SchoolDAO;

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

@Path("/school")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SchoolResource {

    private final SchoolDAO schoolDAO;

    public SchoolResource(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    @POST
    @UnitOfWork
    public School createSchool(School school) {
        return schoolDAO.create(school);
    }

    @GET
    @UnitOfWork
    public List<School> listSchool() {
        return schoolDAO.findAll();
    }


    @GET
    @Path("/{schoolId}")
    @UnitOfWork
    public School getSchool(@PathParam("schoolId") LongParam schoolId) {
        return findSafely(schoolId.get());
    }

    private School findSafely(long schoolId) {
        final Optional<School> school = schoolDAO.findById(schoolId);
        if (!school.isPresent()) {
            throw new NotFoundException("No such school.");
        }
        return school.get();
    }
}