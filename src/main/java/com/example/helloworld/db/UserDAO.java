package com.example.helloworld.db;

import com.google.common.base.Optional;
import com.example.helloworld.core.User;

import org.hibernate.Query;
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

    public User findByCredentials(String username, String password) {
        Query userQuery = namedQuery("com.example.helloworld.core.User.getByUsername");
        userQuery.setParameter("username", username);
        userQuery.setParameter("password", password);
        User user = uniqueResult(userQuery);

        //System.out.println("User id: " + user.getId());
        return user;
    }

    public List<User> searchByUsername(String optional) {
        Query query = namedQuery(
                "com.example.helloworld.core.User.searchByUsername");
        query.setParameter("username", "%" + optional + "%");
        return list(query);
    }

    public List<User> findAll(String username) {
        System.out.println("Username :::::::: " + username);
        if(username == null || username == "") {
            return list(namedQuery("com.example.helloworld.core.User.findAll"));
        }
        return list(namedQuery("com.example.helloworld.core.User.searchByUsername").setParameter("username", username));
    }
}
