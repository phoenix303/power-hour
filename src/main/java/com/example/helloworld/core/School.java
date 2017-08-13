package com.example.helloworld.core;

import javax.persistence.*;

@Entity
@Table(name = "school")
@NamedQueries({
    @NamedQuery(
        name = "com.example.helloworld.core.School.findAll",
        query = "SELECT p FROM School p"
    ),
    @NamedQuery(
        name = "com.example.helloworld.core.School.findById",
        query = "SELECT p FROM School p WHERE p.id = :id"
    )
})
public class School {
    @Id
    @SequenceGenerator(name = "schoolSeq", sequenceName="school_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "schoolSeq")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "website", nullable = false)
    private String website;

    @Column(name = "type", nullable = false)
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
