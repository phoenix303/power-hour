package com.example.helloworld.db;

import com.example.helloworld.core.Company;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class CompanyDAO extends AbstractDAO<Company> {
    public CompanyDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Company> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

    public Company create(Company company) {
        return persist(company);
    }

    public List<Company> findAll() {
        return list(namedQuery("com.example.helloworld.core.Company.findAll"));
    }
}
