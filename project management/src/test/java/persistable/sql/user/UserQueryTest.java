package persistable.sql.user;

import org.example.model.user.User;
import org.example.sql.QueryGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserQueryTest {

    @Test
    public void testInsertQuery() {
        User user = new User(1, "mike98");
        QueryGenerator queryGenerator = new QueryGenerator();

        String query = queryGenerator.generateInsertQuery(user);

        assertEquals("INSERT INTO users (id, username, rolesOnProjects) VALUES (1, 'mike98', NULL);", query);
    }

    @Test
    public void testGetSingleUserQuery() {
        int id = 1;
        QueryGenerator queryGenerator = new QueryGenerator();

        String query = queryGenerator.generateGerUserProjectRoles(id);

        assertEquals("SELECT * FROM user_project_roles WHERE user_id = 1;", query);
    }
}
