package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    @JsonIgnore
    private String password_hash;
    private String name;
    private String role;

    @JsonIgnore // This prevents infinite recursion in JSON
    @OneToMany(mappedBy = "student")
    private Set<InterestedIn> interestedInProjects;

    /**
     * Default constructor; this is required by JPA.
     */
    protected User() {
    }

    public User(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    public User(String username, String password_hash, String name, String role) {
        this.username = username;
        this.password_hash = password_hash;
        this.name = name;
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setInterestedInProjects(Set<InterestedIn> interestedInProjects) {
        this.interestedInProjects = interestedInProjects;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password_hash;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Set<InterestedIn> getInterestedInProjects() {
        return interestedInProjects;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User u = (User) o;

        return this.username == u.username && this.name == u.name && this.role == u.role;
    }
}
