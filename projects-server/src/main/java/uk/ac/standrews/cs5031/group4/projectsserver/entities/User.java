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
    @JsonIgnore
    private String password_hash;
    private String name;
    private String role;

    @JsonIgnore
    @ManyToMany(mappedBy = "interestedStudents")
    private Set<Project> interestedInProjects;

    /**
     * Default constructor; this is required by JPA.
     */
    public User() {
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

    public void setInterestedInProjects(Set<Project> interestedInProjects) {
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

    public Set<Project> getInterestedInProjects() {
        return interestedInProjects;
    }
}
