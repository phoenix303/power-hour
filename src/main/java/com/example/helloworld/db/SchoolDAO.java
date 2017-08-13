package com.example.helloworld.db;

import com.example.helloworld.core.School;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class SchoolDAO extends AbstractDAO<School> {
    public SchoolDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<School> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public School create(School school) {
        return persist(school);
    }

    public List<School> findAll() {
        return list(namedQuery("com.example.helloworld.core.School.findAll"));
    }
}
