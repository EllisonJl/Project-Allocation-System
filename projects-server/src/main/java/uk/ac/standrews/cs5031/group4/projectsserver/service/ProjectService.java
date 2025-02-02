package uk.ac.standrews.cs5031.group4.projectsserver.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedIn;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.InterestedInId;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.InterestedInRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.UserRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestedInRepository interestedInRepository;

    /**
     * Registers student interest in a project.
     *
     * @param username  The username of the student.
     * @param projectId The ID of the project.
     * @throws IllegalStateException if the user or project does not exist, or if the student has already registered interest in the project.
     */
    public void registerStudentInterest(String username, int projectId) {
        User student = userRepository.findById(username)
                .orElseThrow(() -> new IllegalStateException("User does not exist"));
        Project project = projectRepository.findById(String.valueOf(projectId))
                .orElseThrow(() -> new IllegalStateException("Project does not exist"));
        InterestedInId id = new InterestedInId();
        id.setStudent(username);
        id.setProject(projectId);
        if (interestedInRepository.existsById(id)) {
            throw new IllegalStateException("Student has already registered interest in this project");
        }
        InterestedIn newInterest = new InterestedIn();
        newInterest.setStudent(student);
        newInterest.setProject(project);
        interestedInRepository.save(newInterest); // Preserving student interest in the programme
    }

    /**
     * Retrieves projects proposed by a staff member.
     *
     * @param username The username of the staff member.
     * @return A list of projects proposed by the staff member.
     */
    public List<Project> getProjectsProposedByStaff(String username) {
        // Call the repository method directly to get all the items proposed by the
        // faculty member
        List<Project> projects = projectRepository.findByProposedByStaffUsername(username);

        return projects;
    }

    /**
     * Proposes a new project.
     *
     * @param name        The name of the project.
     * @param description The description of the project.
     * @param username    The username of the staff member proposing the project.
     * @return The proposed project.
     * @throws IllegalStateException if the user is not found.
     */
    public Project proposeProject(String name, String description, String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new IllegalStateException("User not found"));
        Project project = new Project(name, description, user);
        Project result = projectRepository.save(project);
        return result;
    }

    /**
     * Accepts a student for a project.
     *
     * @param projectId      The ID of the project.
     * @param studentUsername The username of the student.
     * @throws IllegalStateException if the project is already assigned, if the student hasn't registered interest for the project, or if the student does not exist.
     */
    public void acceptStudent(int projectId, String studentUsername) {
        Project project = projectRepository.findById(String.valueOf(projectId))
                .orElseThrow(() -> new IllegalStateException("Project does not exist"));

        if (project.getAssignedStudent() != null) {
            throw new IllegalStateException("Project is already assigned");
        }

        boolean interestRegistered = interestedInRepository.findById(new InterestedInId(studentUsername, projectId)).isPresent();

        if (!interestRegistered) {
            throw new IllegalStateException("Student hasn't registered their interest for this project");
        }

        User student = userRepository.findById(studentUsername)
                .orElseThrow(() -> new IllegalStateException("Student does not exist"));
        project.setAssignedStudent(student);

        projectRepository.save(project);
    }

}
