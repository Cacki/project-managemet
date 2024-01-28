package model;

import org.example.model.document.Document;
import org.example.model.project.Project;
import org.example.model.user.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DocumentTest {
    @Test
    public void testDocumentCreation() {
        // Arrange
        int documentId = 1;
        String documentTitle = "Sample Document";
        String documentTopic = "Programming";
        String documentContent = "This is the content of the document.";
        String documentDescription = "A sample document description.";
        User documentCreator = new User("John Doe");
        Project documentProject = new Project.ProjectBuilder(1, "Sample Project").build();

        // Act
        Document document = new Document.DocumentBuilder()
                .id(documentId)
                .title(documentTitle)
                .topic(documentTopic)
                .content(documentContent)
                .description(documentDescription)
                .creator(documentCreator)
                .project(documentProject)
                .build();

        // Assert
        assertNotNull(document);
        assertEquals(documentId, document.getId());
        assertEquals(documentTitle, document.getTitle());
        assertEquals(documentTopic, document.getTopic());
        assertEquals(documentContent, document.getContent());
        assertEquals(documentDescription, document.getDescription());
        assertEquals(documentCreator, document.getCreator());
        assertEquals(documentProject, document.getProject());
    }
}
