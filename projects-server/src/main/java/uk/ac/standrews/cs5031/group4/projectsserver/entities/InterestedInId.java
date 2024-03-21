package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.io.Serializable;

public class InterestedInId implements Serializable {
    private String student;
    private int project;

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }

    public InterestedInId(String student, int project) {
        this.student = student;
        this.project = project;
    }

    public InterestedInId() {
    }



}
