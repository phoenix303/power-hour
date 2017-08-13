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
        ),

        @NamedQuery(name = "com.example.helloworld.core.User.searchByUsername",
                query = "SELECT u FROM User u WHERE u.username LIKE :username ORDER BY u.id ASC"),
        @NamedQuery(name = "com.example.helloworld.core.User.getByUsername",
                query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password"),
        @NamedQuery(name = "com.example.helloworld.core.User.deleteUser",
                query = "DELETE FROM User u WHERE u.id = :id")
})
public class User {

    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_EDITOR = "editor";

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName="user_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userSeq")
    private long id;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }
}

