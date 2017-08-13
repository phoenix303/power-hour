package com.example.helloworld.db;

import com.google.common.base.Optional;
import com.example.helloworld.core.User;

import org.hibernate.SessionFactory;
import java.util.List;
import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {
    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<User> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public User create(User user) {
        return persist(user);
    }

    public List<User> findAll() {
        return list(namedQuery("com.example.helloworld.core.User.findAll"));
    }
}
