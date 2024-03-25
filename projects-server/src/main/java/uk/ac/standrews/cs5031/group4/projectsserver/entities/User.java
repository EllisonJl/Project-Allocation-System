package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity class representing a user.
 */
@Entity
@Table(name = "users")
public class User {
    // Username of the user; primary key
    @Id
    private String username;

    // Hashed password of the user; ignored in JSON serialization
    @JsonIgnore
    private String password_hash;

    // Name of the user
    private String name;

    // Role of the user
    private String role;

    // Set of projects that the user is interested in
    @JsonIgnore // This prevents infinite recursion in JSON
    @OneToMany(mappedBy = "student")
    private Set<InterestedIn> interestedInProjects;

    /**
     * Default constructor; required by JPA.
     */
    public User() {
    }

    /**
     * Constructor to initialize User with username, name, and role.
     *
     * @param username The username of the user.
     * @param name The name of the user.
     * @param role The role of the user.
     */
    public User(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role = role;
    }

    /**
     * Constructor to initialize User with username, password hash, name, and role.
     *
     * @param username The username of the user.
     * @param password_hash The hashed password of the user.
     * @param name The name of the user.
     * @param role The role of the user.
     */
    public User(String username, String password_hash, String name, String role) {
        this.username = username;
        this.password_hash = password_hash;
        this.name = name;
        this.role = role;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the name of the user.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the role of the user.
     *
     * @param role The role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Set the set of projects that the user is interested in.
     *
     * @param interestedInProjects The set of interested projects to set.
     */
    public void setInterestedInProjects(Set<InterestedIn> interestedInProjects) {
        this.interestedInProjects = interestedInProjects;
    }

    /**
     * Get the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the hashed password of the user.
     *
     * @return The hashed password.
     */
    public String getPassword() {
        return password_hash;
    }

    /**
     * Get the name of the user.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the role of the user.
     *
     * @return The role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Get the set of projects that the user is interested in.
     *
     * @return The set of interested projects.
     */
    public Set<InterestedIn> getInterestedInProjects() {
        return interestedInProjects;
    }

    // Equals method to check equality based on username, name, and role
    @Override
    public boolean equals(Object o) {
        // Checks if the object is identical to this object
        if (o == this) {
            return true;
        }

        // Checks if the object is null
        if (o == null) {
            return false;
        }

        // Checks if the object is an instance of the User class
        if (!(o instanceof User)) {
            return false;
        }

        // Casts the object to User type
        User u = (User) o;

        // Compares username, name, and role for equality
        return this.username.equals(u.username) && this.name.equals(u.name) && this.role.equals(u.role);
    }
}
