package training.tasks.unit10.db;

import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDataSourceTest {

    private final Flyway flyway;

    protected abstract DataSource getDataSource();

    protected abstract void dropSchema() throws SQLException;

    AbstractDataSourceTest() {
        flyway = new Flyway();
        flyway.setDataSource(getDataSource());
        flyway.setLocations("db.migrate");
    }

    @Before
    public void before() throws SQLException {
        dropSchema();
        flyway.migrate();
    }

    @After
    public void after() throws SQLException {
        flyway.clean();
        dropSchema();
    }

    @Test
    public void createAndGetUsersTest() throws SQLException {
        try (Connection connection = getDataSource().getConnection()) {
            final PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO unit10dbtest.user (id, login, firstName, lastName, email, passwordHash)\n" +
                            "  SELECT nextval('unit10dbtest.user_seq'), 'User1', 'fn1', 'ln1', 's@g.er', 'hash1'\n" +
                            "  UNION ALL SELECT nextval('unit10dbtest.user_seq'), 'User2', 'fn2', 'ln2', 's2@g.er', 'hash2'\n" +
                            "  UNION ALL SELECT nextval('unit10dbtest.user_seq'), 'User3', 'fn3', 'ln3', 's3@g.er', 'hash3';"
            );
            query.executeUpdate();
        }

        try (Connection connection = getDataSource().getConnection()) {
            final PreparedStatement query = connection
                    .prepareStatement("SELECT * FROM unit10dbtest.user;");
            final ResultSet resultSet = query.executeQuery();
            while (resultSet.next()) {
                System.out.printf("id: [%d], login: [%s], firstName: [%s], lastName: [%s], email: [%s], hash: [%s]\n",
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("passwordHash")
                );
            }
        }
    }


}
