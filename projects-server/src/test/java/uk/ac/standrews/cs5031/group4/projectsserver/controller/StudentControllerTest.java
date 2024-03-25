package uk.ac.standrews.cs5031.group4.projectsserver.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.standrews.cs5031.group4.projectsserver.controller.ProjectController;
import uk.ac.standrews.cs5031.group4.projectsserver.service.ProjectService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void acceptStudent_ShouldAcceptStudent_WhenGivenValidData() throws Exception {
    }
}
