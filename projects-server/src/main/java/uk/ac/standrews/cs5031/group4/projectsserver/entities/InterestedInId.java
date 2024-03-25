package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.io.Serializable;

/**
 * Composite key class for the InterestedIn entity.
 * Represents the primary key of the relationship between students and projects.
 */
public class InterestedInId implements Serializable {
    // Username of the student interested in the project
    private String student;

    // ID of the project that the student is interested in
    private int project;

    /**
     * Get the username of the student interested in the project.
     *
     * @return The username.
     */
    public String getStudent() {
        return student;
    }

    /**
     * Set the username of the student interested in the project.
     *
     * @param student The username to set.
     */
    public void setStudent(String student) {
        this.student = student;
    }

    /**
     * Get the ID of the project that the student is interested in.
     *
     * @return The project ID.
     */
    public int getProject() {
        return project;
    }

    /**
     * Set the ID of the project that the student is interested in.
     *
     * @param project The project ID to set.
     */
    public void setProject(int project) {
        this.project = project;
    }

    /**
     * Constructor to initialize InterestedInId with student username and project ID.
     *
     * @param student The username of the student.
     * @param project The ID of the project.
     */
    public InterestedInId(String student, int project) {
        this.student = student;
        this.project = project;
    }

    /**
     * Default constructor.
     */
    public InterestedInId() {
    }
}
