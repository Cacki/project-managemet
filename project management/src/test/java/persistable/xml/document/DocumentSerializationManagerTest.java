package persistable.xml.document;

import org.example.model.document.Document;
import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.xml.SerializationManager;
import org.junit.*;

import java.util.List;

public class DocumentSerializationManagerTest {
    private static Document document;
    private static SerializationManager manager;

    @BeforeClass
    public static void setupBeforeClass() {
        manager = new SerializationManager();
    }

    @Before
    public void setupBefore() {
        int documentId = 1;
        String documentTitle = "Sample Document";
        String documentTopic = "Programming";
        String documentContent = "This is the content of the document.";
        String documentDescription = "A sample document description.";
        User documentCreator = new User(1);
        Project documentProject = new Project.ProjectBuilder(1).build();

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
        manager = null;
    }

    @Test
    public void serialize() {
        String serialized = manager.serializeObject(document);
        String expected = "<Document><id>1</id><title>Sample Document</title><topic>Programming</topic>" +
                "<content>This is the content of the document.</content><description>A sample document description.</description>" +
                "<user_id>1</user_id><project_id>1</project_id></Document>";
        Assert.assertEquals(expected, serialized);
    }

    @Test
    public void deserialize() {
        String serialized = "<Document><id>1</id><title>Sample Document</title><topic>Programming</topic>" +
                "<content>This is the content of the document.</content><description>A sample document description.</description>" +
                "<user_id>1</user_id><project_id>1</project_id></Document>";

        Document deserialized = manager.deserializeObject(serialized, document.getClass());

        Assert.assertEquals(deserialized.getId(), document.getId());
    }

    @Test
    public void deserializeFile() {
        String serialized = "<Document><id>1</id><title>Sample Document</title><topic>Programming</topic>" +
                "<content>This is the content of the document.</content><description>A sample document description.</description>" +
                "<user_id>1</user_id><project_id>1</project_id></Document>" +
                "<Document><id>2</id><title>Sample Document 2</title><topic>Programming 2</topic>" +
                "<content>This is the content of the document 2.</content><description>A sample document description 2.</description>" +
                "<user_id>2</user_id><project_id>2</project_id></Document>";

        List<? extends Document> documents = manager.deserializeFile(serialized, document.getClass());

        Assert.assertEquals(2, documents.size());
    }
}
