package persistable.sql.document;

import org.example.model.document.Document;
import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.sql.QueryGenerator;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class DocumentQueryTest {

    private static Document document;
    private static QueryGenerator queryGenerator;

    @BeforeClass
    public static void setupBeforeClass() {
        queryGenerator = new QueryGenerator();
    }

    @Before
    public void setupBefore() {
        int documentId = 1;
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
    public void tearDownAfter() {
        document = null;
    }

    @AfterClass
    public static void tearDownAfterClass() {
        document = null;
        queryGenerator = null;
    }

    @Test
    public void testInsert() {
        String expected = "INSERT INTO documents (id, title, topic, content, description, user_id, project_id) " +
                "VALUES (1, 'Sample Document', 'Programming', 'This is the content of the document.', " +
                "'A sample document description.', 1, 1);";

        String query = queryGenerator.generateInsertQuery(document);

        assertEquals(expected, query);
    }

    @Test
    public void testUpdate() {
        String expected = "UPDATE documents SET title = 'Sample Document', topic = 'Programming', " +
                "content = 'This is the content of the document.', description = 'A sample document description.'," +
                " user_id = 1, project_id = 1 WHERE id = 1;";

        String query = queryGenerator.generateUpdateQuery(document);

        assertEquals(expected, query);
    }

    @Test
    public void testDelete() {
        String expected = "DELETE FROM documents WHERE id = 1";

        String query = queryGenerator.generateDeleteQuery(document);

        assertEquals(expected, query);
    }

    @Test
    public void testSelect() {
        String expected = "SELECT * FROM documents";

        String query = queryGenerator.generateSelectQuery(document);

        assertEquals(expected, query);
    }
}
