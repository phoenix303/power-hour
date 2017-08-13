package com.example.helloworld.auth;

import com.google.common.base.Optional;

import com.example.helloworld.core.User;
import com.example.helloworld.db.UserDAO;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;

public class PowerHourAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDAO userDAO;
    private final PasswordUtil util;

    public PowerHourAuthenticator(UserDAO userDAO, PasswordUtil util) {
        this.userDAO = userDAO;
        this.util = util;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {

        User loggedInUser = userDAO.findByCredentials(basicCredentials.getUsername());

        if(loggedInUser == null || !util.authenticate(basicCredentials.getPassword().toCharArray(), loggedInUser.getPassword())) {
            return Optional.absent();
        }

        return Optional.of(loggedInUser);
    }
}


