package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller class for handling HTTP requests related to projects.
 */
@RestController // Indicates that this class is a REST controller.
// prevents CORS errors when accessing the API
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired // Automatically injects an instance of ProjectService.
    private ProjectService projectService;

    @Autowired // Automatically injects an instance of ProjectRepository.
    private ProjectRepository projectRepository;

    /**
     * Endpoint to fetch all available projects.
     *
     * @return ResponseEntity containing the list of available projects.
     */
    @GetMapping("/available-projects") // Maps HTTP GET requests for "/available-projects" to this method.
    public ResponseEntity<List<Project>> getAvailableProjects() {
        // Fetch all projects that haven't been assigned to anyone
        List<Project> availableProjects = projectRepository.findByAssignedStudentIsNull();
        return ResponseEntity.ok(availableProjects); // Returns the list of available projects in the response body.
    }

    /**
     * Endpoint to fetch a project by its ID.
     *
     * @param id The ID of the project to fetch.
     * @return ResponseEntity containing the project if found, or 404 if not found.
     */
    @GetMapping("/projects/{id}") // Maps HTTP GET requests for "/projects/{id}" to this method.
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        Optional<Project> requiredProject = projectRepository.findById(id);

        if (requiredProject.isPresent()) {
            return ResponseEntity.ok(requiredProject.get()); // Returns the project if found.
        } else {
            return ResponseEntity.notFound().build(); // Returns 404 if the project is not found.
        }
    }

    /**
     * Endpoint to register interest in a project.
     *
     * @param payload The request body containing the project ID.
     * @param auth The authentication object representing the logged in user.
     * @return ResponseEntity with status and optional message.
     */
    @PostMapping("/register-interest")
    @Secured("student") // Specifies that only users with the "student" role can access this endpoint.
    public ResponseEntity<?> registerInterest(@RequestBody Map<String, Object> payload, Authentication auth) {
        String username = auth.getName();
        int projectId = (Integer) payload.get("project_id");

        try {
            projectService.registerStudentInterest(username, projectId);
            return ResponseEntity.ok().build(); // Returns 200 OK if registration is successful.
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()); // Returns 403 Forbidden if registration fails.
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage()); // Returns 500 Internal Server Error for other exceptions.
        }
    }

    /**
     * Endpoint to fetch proposed projects.
     *
     * @param auth The authentication object representing the logged in user.
     * @return ResponseEntity containing the list of proposed projects.
     */
    @GetMapping("/proposed-projects")
    @Secured("staff") // Specifies that only users with the "staff" role can access this endpoint.
    public ResponseEntity<List<Project>> getProposedProjects(Authentication auth) {
        String username = auth.getName();
        try {
            List<Project> projects = projectService.getProjectsProposedByStaff(username);
            return ResponseEntity.ok(projects); // Returns the list of proposed projects.
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null); // Returns 500 Internal Server Error if an exception occurs.
        }
    }

    /**
     * Endpoint to propose a new project.
     *
     * @param payload The request body containing the project name and description.
     * @param principal The principal object representing the logged in user.
     * @return ResponseEntity with status and optional message.
     */
    /**
     * Endpoint to propose a new project.
     *
     * @param payload   The request body containing the project name and description.
     * @param principal The principal object representing the logged-in user.
     * @return ResponseEntity with status and optional message.
     */
    @PostMapping("/projects")
    public ResponseEntity<?> proposeProject(@RequestBody Map<String, String> payload, Principal principal) {
        // Extract project name from request body
        String name = payload.get("name");
        // Extract project description from request body
        String description = payload.get("description");
        // Get the username of the currently logged-in user
        String username = principal.getName();

        try {
            // Attempt to propose the project via the service layer
            Project project = projectService.proposeProject(name, description, username);
            // Return a response entity with HTTP status 201 CREATED and the created project
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (IllegalStateException e) {
            // Return a response entity with HTTP status 400 BAD REQUEST and the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint to accept a student for a project.
     *
     * @param id      The ID of the project.
     * @param payload The request body containing the student username.
     * @return ResponseEntity with status and optional message.
     */
    @PostMapping("projects/{id}/accept-student")
    public ResponseEntity<?> acceptStudent(@PathVariable int id, @RequestBody Map<String, String> payload) {
        try {
            // Extract student username from the request body
            String studentUsername = payload.get("student_username");
            // Call the service layer to accept the student for the project
            projectService.acceptStudent(id, studentUsername);
            // Return a response entity with HTTP status 200 OK
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            // Return a response entity with HTTP status 400 BAD REQUEST and the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
