package com.example.helloworld.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;

import java.sql.Time;
import java.util.Date;

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
@Table(name = "events")
@NamedQueries({
        @NamedQuery(
                name = "com.example.helloworld.core.Event.findAll",
                query = "SELECT v FROM Event v"
        ),
        @NamedQuery(
                name = "com.example.helloworld.core.Event.findById",
                query = "SELECT m FROM Event m WHERE v.id = :id"
        )
})
public class Event {

    // TODO : id//
    @Id
    @SequenceGenerator(name = "personSeq", sequenceName="person_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "personSeq")
    private Long id;

    @Length(max = 20)
    private String title;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Time time;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true)
    private String website;

    @Column(nullable = false)
    private String description;

    public Event(Long id, String title, String type, Date date, Time time, String location,
                 String website, String description) {
        // Jackson deserialization
        this.id = id;
        this.title = title;
        this.type = type;
        this.date = date;
        this.time = time;
        this.location = location;
        this.website = website;
        this.description = description;

    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonIgnore
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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
