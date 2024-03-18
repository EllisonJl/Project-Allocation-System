package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String username;
    private String name;
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "interestedStudents")
    private Set<Project> interestedInProjects;

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

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Set<Project> getInterestedInProjects() {
        return interestedInProjects;
    }
}
