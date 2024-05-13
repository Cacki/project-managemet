package sql;

import org.example.model.document.Document;
import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.sql.QueryGenerator;
import org.example.sql.SQLConnection;
import org.example.sql.SqlManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class SQLManagerTest {
    private static SqlManager manager;

    @BeforeClass
    public static void setUp() throws SQLException {
        manager = new SqlManager(new QueryGenerator());
        SQLConnection.connection.setAutoCommit(false);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        SQLConnection.connection.rollback();
        SQLConnection.connection.close();
    }

    @Test
    public void testCreate() {
        int documentId = 99;
        String documentTitle = "Sample Document";
        String documentTopic = "Programming";
        String documentContent = "This is the content of the document.";
        String documentDescription = "A sample document description.";
        User documentCreator = new User(1, "John Doe");
        Project documentProject = new Project.ProjectBuilder(1, "Sample Project").build();

        Document document = new Document.DocumentBuilder()
                .id(documentId)
                .title(documentTitle)
                .topic(documentTopic)
                .content(documentContent)
                .description(documentDescription)
                .creator(documentCreator)
                .project(documentProject)
                .build();

        manager.create(document);
    }
}