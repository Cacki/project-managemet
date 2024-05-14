package sql;

import org.example.model.document.Document;
import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.sql.QueryGenerator;
import org.example.sql.SQLConnection;
import org.example.sql.SqlManager;
import org.junit.*;

import java.sql.SQLException;

public class SQLManagerTest {
    private static SqlManager manager;
    private static Document document;

    @BeforeClass
    public static void setupBefore() throws SQLException {
        manager = new SqlManager(new QueryGenerator());
        SQLConnection.connection.setAutoCommit(false);
    }

    @AfterClass
    public static void tearDownAfter() throws SQLException {
        manager = null;
        document = null;
        SQLConnection.connection.rollback();
        SQLConnection.connection.close();
    }

    @Before
    public void setup() {
        int documentId = 99;
        String documentTitle = "Sample Document";
        String documentTopic = "Programming";
        String documentContent = "This is the content of the document.";
        String documentDescription = "A sample document description.";
        User documentCreator = new User(1, "John Doe");
        Project documentProject = new Project.ProjectBuilder(1, "Sample Project").build();

        document = new Document.DocumentBuilder()
                .id(documentId)
                .title(documentTitle)
                .topic(documentTopic)
                .content(documentContent)
                .description(documentDescription)
                .creator(documentCreator)
                .project(documentProject)
                .build();
    }

    @After
    public void terDown() {
        document = null;
    }

    @Ignore
    @Test
    public void testCreate() {
        manager.create(document);
    }

    @Ignore
    @Test
    public void testUpdate() {
        manager.create(document);
        document.setTitle("Updated title");
        manager.update(document);
    }
}