package com.example.helloworld.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(
                name = "com.example.helloworld.core.User.findAll",
                query = "SELECT u FROM User u"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.User.findById",
                query = "SELECT u FROM User u WHERE u.id = :id"
        )
})
public class User {
    @Id
    @SequenceGenerator(name = "personSeq", sequenceName="person_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "personSeq")
    private Long id;

    @Column(name = "fullName", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


    public User(Long id, String fullName, String username, String password) {
        this.id = new Long(id);
        this.fullName = fullName;
        this.username = username;
        this.password = password;

    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

