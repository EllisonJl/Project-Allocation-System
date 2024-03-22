package uk.ac.standrews.cs5031.group4.projectsserver.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void registerStudentInterest(String username, int projectId) {
        User student = userRepository.findById(username).orElseThrow(() -> new IllegalStateException("User does not exist"));
        Project project = projectRepository.findById(String.valueOf(projectId)).orElseThrow(() -> new IllegalStateException("Project does not exist"));
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

    public List<Project> getProjectsProposedByStaff(String username) {
        // Call the repository method directly to get all the items proposed by the faculty member
        List<Project> projects = projectRepository.findByProposedByStaffUsername(username);

        return projects;
    }
    @Transactional
    public Project proposeProject(String name, String description, String username) {
        User user = userRepository.findById(username).orElseThrow(() -> new IllegalStateException("User not found"));
        Project project = new Project(name, description, user);
        return projectRepository.save(project);
    }
    @Transactional
    public void acceptStudent(int projectId, String studentUsername) {
        Project project = projectRepository.findById(String.valueOf(projectId))
                .orElseThrow(() -> new IllegalStateException("Project does not exist"));

        if (project.getAssignedStudent() != null) {
            throw new IllegalStateException("Project is already assigned");
        }

        boolean interestRegistered = interestedInRepository.findByStudentUsernameAndProjectId(studentUsername, projectId).isPresent();
        if (!interestRegistered) {
            throw new IllegalStateException("Student hasn't registered their interest for this project");
        }

        User student = userRepository.findById(studentUsername)
                .orElseThrow(() -> new IllegalStateException("Student does not exist"));
        project.setAssignedStudent(student);

        projectRepository.save(project);
    }
}
