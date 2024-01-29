package persistable;

import org.example.model.user.UserDTO;
import org.example.persistance.QueryGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserQueryTest {

    @Test
    public void testInsertQuery() {
        UserDTO user = new UserDTO(1, "mike98");

        String query = QueryGenerator.generateInsertQuery(user);

        assertEquals("INSERT INTO users (id, username) VALUES ('1', 'mike98');", query);
    }
}
