package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String description;
    public Project(String name, String description, User proposedByStaff) {
        this.name = name;
        this.description = description;
        this.proposedByStaff = proposedByStaff;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setProposedByStaff(User proposedByStaff) {
        this.proposedByStaff = proposedByStaff;
    }


    @ManyToOne
    @JoinColumn(name = "proposed_by", nullable = false)
    private User proposedByStaff;

    @OneToOne
    @JoinColumn(name = "assigned_to", nullable = true)
    private User assignedStudent;

    @ManyToMany
    @JoinTable(name = "interested_in", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "student_username"))
    private Set<User> interestedStudents;




    public void setAssignedStudent(User assignedStudent) {
        this.assignedStudent = assignedStudent;
    }

    public void setInterestedStudents(Set<User> interestedStudents) {
        this.interestedStudents = interestedStudents;
    }

    /**
     * Default constructor; this is required by JPA.
     */
    public Project() {
    }

    public Project(String name, String description, User proposedByStaff, User assignedStudent) {
        this.name = name;
        this.description = description;
        this.proposedByStaff = proposedByStaff;
        this.assignedStudent = assignedStudent;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getProposedByStaff() {
        return proposedByStaff;
    }

    public User getAssignedStudent() {
        return assignedStudent;
    }

    public Set<User> getInterestedStudents() {
        return interestedStudents;
    }


}
