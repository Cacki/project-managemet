package model;

import org.example.model.document.Document;
import org.example.model.user.User;
import org.example.model.project.Project;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProjectTest {
    @Test
    public void testProjectCreation() {
        // Arrange
        int projectId = 1;
        String projectName = "Sample Project";
        String projectDescription = "A sample project description";
        User projectCreator = new User("John Doe");
        Set<Document> projectDocuments = new HashSet<>();

        // Act
        Project project = new Project.ProjectBuilder(projectId, projectName)
                .description(projectDescription)
                .creator(projectCreator)
                .documents(projectDocuments)
                .build();

        // Assert
        assertNotNull(project);
        assertEquals(projectId, project.getId());
        assertEquals(projectName, project.getName());
        assertEquals(projectDescription, project.getDescription());
        assertEquals(projectCreator, project.getCreator());
        assertEquals(projectDocuments, project.getDocuments());
    }
}
