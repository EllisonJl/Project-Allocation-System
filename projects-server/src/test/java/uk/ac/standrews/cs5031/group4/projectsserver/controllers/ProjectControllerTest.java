package uk.ac.standrews.cs5031.group4.projectsserver.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.Project;
import uk.ac.standrews.cs5031.group4.projectsserver.entities.User;
import uk.ac.standrews.cs5031.group4.projectsserver.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProjectControllerTest {

    @MockBean
    ProjectRepository projectRepository;

    @Autowired
    ProjectController projectController;

    @Test
    void getAvailableProjectsWhenNoProjectsAreAvailable(){
        when(projectRepository.findByAssignedStudentIsNull()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Project>> responseEntity = projectController.getAvailableProjects();
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertFalse(responseEntity.hasBody());
        verify(projectRepository,Mockito.times(1)).findByAssignedStudentIsNull();
    }

    @Test
    void getAvailableProjectsWhenProjectsAreAvailable() {
        List<Project> projects = new ArrayList<>();
        Project project =new Project("Foobar", "This project aims to extend the Foobar project to add Gigatron features.", new User("jbloggs", "Joe Bloggs", "staff"),null);
        projects.add(project);

        when(projectRepository.findByAssignedStudentIsNull()).thenReturn(projects);

        ResponseEntity<List<Project>> responseEntity = projectController.getAvailableProjects();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertTrue(responseEntity.hasBody());
        assertEquals(projects,responseEntity.getBody());
        System.out.println(responseEntity.getBody());
        verify(projectRepository,Mockito.times(2)).findByAssignedStudentIsNull(); // the function is actually called two times. One is to check if the project repo is empty and other is to get the list of students
    }

    @Test
    void getProjectByIdWhenProjectIsPresent(){
        Project expectedProject = new Project("Foobar", "This project aims to extend the Foobar project to add Gigatron features.", new User("jbloggs", "Joe Bloggs", "staff"),null);
        int projectId = expectedProject.getId();
        when(projectRepository.findById(Integer.toString(projectId))).thenReturn(Optional.of(expectedProject));

        ResponseEntity<Project> responseEntity = projectController.getProjectById(Integer.toString(projectId));
        System.out.println(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProject, responseEntity.getBody());
    }

    @Test
    void getProjectByIdWhenProjectIsNotPresent(){
        int invalidId = 21;
        when(projectRepository.findById(Integer.toString(invalidId))).thenReturn(Optional.empty());

        ResponseEntity<Project> responseEntity = projectController.getProjectById(Integer.toString(invalidId));
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}