package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "interested_in")
@IdClass(InterestedInId.class)
public class InterestedIn {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private User student;

    @JsonIgnore // This prevents infinite recursion in JSON
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    public InterestedIn() {
    }

    public InterestedIn(User student, Project project) {
        this.project = project;
        this.student = student;
    }

    // Getters and setters
    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
