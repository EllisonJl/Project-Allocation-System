package uk.ac.standrews.cs5031.group4.projectsserver.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entity class representing a project.
 */
@Entity
@Table(name = "projects")
public class Project {
    // Primary key ID for the project
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    // Name of the project
    private String name;

    // Description of the project
    private String description;

    // Staff member who proposed the project
    @ManyToOne
    @JoinColumn(name = "proposed_by", nullable = false)
    @JsonProperty("proposed_by")
    private User proposedByStaff;

    // Student assigned to the project
    @OneToOne
    @JoinColumn(name = "assigned_to", nullable = true)
    @JsonProperty("assigned_to")
    private User assignedStudent;

    // Set of students interested in the project
    @OneToMany(mappedBy = "project")
    @JsonProperty("interested_students")
    private Set<InterestedIn> interestedStudents;

    /**
     * Default constructor; required by JPA.
     */
    protected Project() {
    }

    /**
     * Constructor to initialize Project with name, description, and proposed by staff.
     *
     * @param name The name of the project.
     * @param description The description of the project.
     * @param proposedByStaff The staff member who proposed the project.
     */
    public Project(String name, String description, User proposedByStaff) {
        this.name = name;
        this.description = description;
        this.proposedByStaff = proposedByStaff;
    }

    /**
     * Constructor to initialize Project with name, description, proposed by staff, and assigned student.
     *
     * @param name The name of the project.
     * @param description The description of the project.
     * @param proposedByStaff The staff member who proposed the project.
     * @param assignedStudent The student assigned to the project.
     */
    public Project(String name, String description, User proposedByStaff, User assignedStudent) {
        this.name = name;
        this.description = description;
        this.proposedByStaff = proposedByStaff;
        this.assignedStudent = assignedStudent;
    }

    /**
     * Get the ID of the project.
     *
     * @return The project ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the project.
     *
     * @return The project name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the project.
     *
     * @return The project description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the staff member who proposed the project.
     *
     * @return The staff member who proposed the project.
     */
    public User getProposedByStaff() {
        return proposedByStaff;
    }

    /**
     * Get the student assigned to the project.
     *
     * @return The assigned student.
     */
    public User getAssignedStudent() {
        return assignedStudent;
    }

    /**
     * Get the set of students interested in the project.
     *
     * @return The set of interested students.
     */
    public Set<InterestedIn> getInterestedStudents() {
        return interestedStudents;
    }

    /**
     * Set the name of the project.
     *
     * @param name The project name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the description of the project.
     *
     * @param description The project description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAssignedStudent(User assignedStudent) {
        this.assignedStudent = assignedStudent;
    }

    /**
     * Set the staff member who proposed the project.
     *
     * @param proposedByStaff The staff member to set.
     */
    public void setProposedByStaff(User proposedByStaff) {
        this.proposedByStaff = proposedByStaff;
    }

    /**
     * Equals method to check equality based on ID, name, and description.
     *
     * @param o The object to compare.
     * @return True if the objects are equal based on ID, name, and description; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        // Check if the objects are identical
        if (o == this) {
            return true;
        }

        // Check if the object is null
        if (o == null) {
            return false;
        }

        // Check if the object is an instance of Project class
        if (!(o instanceof Project)) {
            return false;
        }

        // Cast the object to Project type
        Project p = (Project) o;

        // Compare ID, name, and description for equality
        return this.id == p.id && this.name.equals(p.name) && this.description.equals(p.description);
    }

}
