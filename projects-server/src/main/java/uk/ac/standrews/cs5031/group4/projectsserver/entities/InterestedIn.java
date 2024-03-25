package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

/**
 * Entity class representing the relationship between students and projects they are interested in.
 */
@Entity
@Table(name = "interested_in")
@IdClass(InterestedInId.class)
public class InterestedIn {

    // Primary key representing the student interested in a project
    @Id
    @ManyToOne
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private User student;

    // Primary key representing the project that the student is interested in
    @JsonIgnore // This prevents infinite recursion in JSON
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    /**
     * Default constructor.
     */
    public InterestedIn() {
    }

    /**
     * Constructor to initialize InterestedIn with a student and a project.
     *
     * @param student The student interested in the project.
     * @param project The project that the student is interested in.
     */
    public InterestedIn(User student, Project project) {
        this.project = project;
        this.student = student;
    }

    /**
     * Get the student interested in the project.
     *
     * @return The student.
     */
    @JsonValue // When serialising to JSON, just serialise as a Student object, ignoring the project.
    public User getStudent() {
        return student;
    }

    /**
     * Set the student interested in the project.
     *
     * @param student The student to set.
     */
    public void setStudent(User student) {
        this.student = student;
    }

    /**
     * Get the project that the student is interested in.
     *
     * @return The project.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Set the project that the student is interested in.
     *
     * @param project The project to set.
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
