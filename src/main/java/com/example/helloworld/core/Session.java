package com.example.helloworld.core;

import java.util.UUID;
import javax.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "session")
@NamedQueries({
        @NamedQuery(
                name = "com.example.helloworld.core.Session.findByIdentity",
                query = "DELETE FROM Session s where s.identity = :identity"
        )
})
public class Session {

    @Id
    @Column(name = "identity", nullable = false)
    private String identity;

    @Column(name = "accesstoken", nullable = false)
    private String accesstoken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "role", nullable = false)
    private String role;

    public Session() {
        
    }

    public Session(String username) {
        this.identity = username;
        this.accesstoken = UUID.randomUUID().toString().substring(0, 23);
        this.created = new Date();
    }

    public Session(String username, String accesstoken) {
        this.identity = username;
        this.accesstoken = accesstoken;
        this.created = new Date();
    }

    public Session(String username, String accesstoken, Date created) {
        this.identity = username;
        this.accesstoken = accesstoken;
        this.created = created;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
