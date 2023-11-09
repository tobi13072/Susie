package io.github.lizewskik.susieserver.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.lizewskik.susieserver.config.TestConfiguration;
import io.github.lizewskik.susieserver.resource.domain.Backlog;
import io.github.lizewskik.susieserver.resource.domain.Project;
import io.github.lizewskik.susieserver.resource.dto.ProjectDTO;
import io.github.lizewskik.susieserver.resource.repository.ProjectRepository;
import io.github.lizewskik.susieserver.resource.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.github.lizewskik.susieserver.builder.ProjectBuilder.PROJECT_DESCRIPTION;
import static io.github.lizewskik.susieserver.builder.ProjectBuilder.PROJECT_GOAL;
import static io.github.lizewskik.susieserver.builder.ProjectBuilder.PROJECT_NAME;
import static io.github.lizewskik.susieserver.builder.UserBuilder.createCurrentLoggedInUser;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@EnableJpaAuditing
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {TestConfiguration.class})
public class ProjectRestControllerIntegrationTest {

    private static final String PROJECT_CONTROLLER_BASE_URL = "/api/scrum-project";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        when(userService.getCurrentLoggedUser()).thenReturn(createCurrentLoggedInUser());
    }

    @Test
    @WithMockUser(username = "scrum_master", roles = "sm")
    public void createProject_returnsProjectDTOResponseAndHttpStatusCodeCreated() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        mvc.perform(post(PROJECT_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDefaultProjectCreationBody())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(PROJECT_NAME)))
                .andExpect(jsonPath("$.description", is(PROJECT_DESCRIPTION)))
                .andExpect(jsonPath("$.projectGoal", is(PROJECT_GOAL)));

        List<Project> allProjects = projectRepository.findAll();
        assertThat(allProjects).extracting(Project::getName).containsOnly(PROJECT_NAME);
    }

    @Test
    @WithMockUser(username = "plain_user", roles = "dev")
    public void getAllProjects_returnsProjectDTOResponseAndReturnsHttpStatusCode200() throws Exception {

        Project project = createDefaultProject(Set.of(createCurrentLoggedInUser().getUuid()));

        mvc.perform(get(PROJECT_CONTROLLER_BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].projectID", is(project.getId())))
                .andExpect(jsonPath("$[0].name", is(project.getName())))
                .andExpect(jsonPath("$[0].description", is(project.getDescription())))
                .andExpect(jsonPath("$[0].projectGoal", is(project.getProjectGoal())));
    }

    private ProjectDTO createDefaultProjectCreationBody() {

        return ProjectDTO.builder()
                .name(PROJECT_NAME)
                .description(PROJECT_DESCRIPTION)
                .projectGoal(PROJECT_GOAL)
                .build();
    }

    private Project createDefaultProject(Set<String> users) {

        Project project = Project.builder()
                .name(PROJECT_NAME)
                .description(PROJECT_DESCRIPTION)
                .projectGoal(PROJECT_GOAL)
                .sprints(new HashSet<>())
                .backlog(new Backlog())
                .userIDs(new HashSet<>(users))
                .build();
        return projectRepository.save(project);
    }
}
