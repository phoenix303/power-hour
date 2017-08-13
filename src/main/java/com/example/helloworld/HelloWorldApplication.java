package com.example.helloworld;

//import com.example.helloworld.auth.PowerHourAuthenticator;

import com.example.helloworld.cli.RenderCommand;
import com.example.helloworld.core.Person;
import com.example.helloworld.core.School;
import com.example.helloworld.core.Company;
import com.example.helloworld.core.Event;
import com.example.helloworld.core.User;
import com.example.helloworld.core.Session;


import com.example.helloworld.core.Template;
import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.db.SchoolDAO;
import com.example.helloworld.db.CompanyDAO;
import com.example.helloworld.db.EventDAO;
import com.example.helloworld.db.UserDAO;
import com.example.helloworld.db.SessionDAO;

import com.example.helloworld.health.TemplateHealthCheck;
import com.example.helloworld.resources.*;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldConfiguration.class);

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle =
            new HibernateBundle<HelloWorldConfiguration>(Person.class, School.class, Company.class, Event.class, User.class, Session.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };


    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        LOGGER.info("Initializing configuration");
        bootstrap.addCommand(new RenderCommand());
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<HelloWorldConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
       // bootstrap.addBundle(hibernateBundleSchool);
        bootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
        LOGGER.info("Starting the HelloWorld App");
        final PersonDAO dao = new PersonDAO(hibernateBundle.getSessionFactory());

        final SchoolDAO schoolDAO = new SchoolDAO(hibernateBundle.getSessionFactory());

        final CompanyDAO companyDAO = new CompanyDAO(hibernateBundle.getSessionFactory());

        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());

        final EventDAO eventDAO = new EventDAO(hibernateBundle.getSessionFactory());

        final SessionDAO sessionDAO = new SessionDAO(hibernateBundle.getSessionFactory());


        final Template template = configuration.buildTemplate();

        environment.healthChecks().register("template", new TemplateHealthCheck(template));

       // environment.jersey().register(new BasicAuthProvider<>(new PowerHourAuthenticator(userDAO, new PasswordUtil(15)), "SUPER SECRET STUFF"));

        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ViewResource());
       // environment.jersey().register(new ProtectedResource());
        environment.jersey().register(new PeopleResource(dao));
        environment.jersey().register(new PersonResource(dao));
        environment.jersey().register(new SchoolResource(schoolDAO));
        environment.jersey().register(new CompanyResource(companyDAO));

        environment.jersey().register(new UserResource(userDAO, eventDAO));

        environment.jersey().register(new SessionResource(userDAO, sessionDAO));

        environment.jersey().register(new EventResource(eventDAO));


    }
}
