package com.example.helloworld.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.helloworld.core.Saying;
import com.example.helloworld.core.Template;
import com.example.helloworld.core.Company;
import com.example.helloworld.db.CompanyDAO;

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

@Path("/company")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {

    private final CompanyDAO companyDAO;

    public CompanyResource(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @POST
    @UnitOfWork
    public Company createCompany(Company company) {
        return companyDAO.create(company);
    }

    @GET
    @UnitOfWork
    public List<Company> listCompany() {
        return companyDAO.findAll();
    }


    @GET
    @Path("/{companyId}")
    @UnitOfWork
    public Company getCompany(@PathParam("companyId") LongParam companyId) {
        return findSafely(companyId.get());
    }

    private Company findSafely(long companyId) {
        final Optional<Company> company = companyDAO.findById(companyId);
        if (!company.isPresent()) {
            throw new NotFoundException("No such company.");
        }
        return company.get();
    }
}