package persistable.xml.document;

import org.example.model.Status.OperationStatus;
import org.example.model.document.Document;
import org.example.model.project.Project;
import org.example.model.user.User;
import org.example.xml.XMLManager;
import org.junit.*;

import java.util.List;

public class DocumentXMLManagerTest {
    private static Document document;
    private static XMLManager manager;

    @BeforeClass
    public static void setupBeforeClass() {
        manager = new XMLManager();
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
    public void create() {
        OperationStatus operationStatus = manager.create(document);
        Assert.assertEquals(operationStatus, OperationStatus.SUCCESS);
    }

    @Ignore
    @Test
    public void read() {
        List<? extends Document> list = manager.read(document.getClass());
        Assert.assertEquals(3, list.size());
    }

    @Ignore
    @Test
    public void update() {
        OperationStatus operationStatus = manager.update(document);
        Assert.assertEquals(operationStatus, OperationStatus.SUCCESS);
    }

    @Ignore
    @Test
    public void delete() {
        OperationStatus operationStatus = manager.delete(document);
        Assert.assertEquals(operationStatus, OperationStatus.SUCCESS);
    }

}
