package com.example.helloworld.db;

import com.example.helloworld.core.User;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;

public class UserAuthDAO extends AbstractDAO<User> {

    public UserAuthDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    // Find User by ID
    public User findById(Long id) {
        return get(id);
    }

    // Create new User
    public User create(User user) {
        return persist(user);
    }

    // Update User with given information (have to check how it works)
    public User update(User user) {
        currentSession().clear();
        return persist(user);
    }

    public boolean deleteUser(Long id) {
        User user = get(id);
        if(user == null) {
            return false;
        }
        else {
            super.currentSession().delete(user);
            return true;
        }
    }

    public User findByCredentials(String username) {
        Query userQuery =
                super.namedQuery("com.example.helloworld.core.User.getByUsername")
                        .setParameter("username", username);
        User user = super.uniqueResult(userQuery);

        return user;
    }

    public List<User> searchByUsername(String optional, int start, int size) {
        Query query = super.namedQuery(
                "com.example.helloworld.core.User.searchByUsername");
        query.setParameter("username", "%" + optional + "%")
                .setFirstResult(start).setMaxResults(size);
        return list(query);
    }
}