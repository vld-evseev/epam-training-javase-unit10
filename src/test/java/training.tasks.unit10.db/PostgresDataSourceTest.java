package training.tasks.unit10.db;

import org.junit.BeforeClass;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresDataSourceTest extends AbstractDataSourceTest {

    private static DataSource dataSource;

    @BeforeClass
    public static void startup() {
        final PostgresDataSourceProvider provider =
                new PostgresDataSourceProvider(new PGConfig() {
                    @Override
                    public String getHost() {
                        return "localhost";
                    }

                    @Override
                    public int getPort() {
                        return 5432;
                    }

                    @Override
                    public String getDbName() {
                        return "unit10dbtest";
                    }

                    @Override
                    public String getUser() {
                        return "postgres";
                    }

                    @Override
                    public String getPassword() {
                        return "pass";
                    }
                });
        dataSource = provider.get();
    }

    @Override
    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected void dropSchema() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final PreparedStatement query = connection.prepareStatement(
                    "DROP SCHEMA IF EXISTS unit10dbtest CASCADE;"
            );
            query.execute();

        }
    }
}
