package com.example.helloworld.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "events")
@NamedQueries({
        @NamedQuery(
                name = "com.example.helloworld.core.Event.findAll",
                query = "SELECT v FROM Event v"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Event.findById",
                query = "SELECT m FROM Event m WHERE m.id = :id"
        ),
        @NamedQuery(name = "com.example.helloworld.core.Event.searchByName",
                query = "SELECT u FROM Event u WHERE u.name LIKE :name ORDER BY u.id ASC"),
})
public class Event {

    // TODO : id//
    @Id
    @SequenceGenerator(name = "eventSeq", sequenceName="event_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "eventSeq")
    private Long id;

    @Length(max = 50)
    private String name;

    @Column(nullable = false)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateTime() {
        return datetime;
    }

    public void setDateTime(Date datetime) {
        this.datetime = datetime;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true)
    private String website;

    @Column(nullable = false)
    private String description;


    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
