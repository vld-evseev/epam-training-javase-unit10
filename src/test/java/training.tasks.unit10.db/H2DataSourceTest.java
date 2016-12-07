package training.tasks.unit10.db;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class H2DataSourceTest extends AbstractDataSourceTest {

    private static DataSource dataSource;

    @BeforeClass
    public static void startup() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:mem:unit10db_mem;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        dataSource.setUser("user");
        dataSource.setPassword("pass");
        H2DataSourceTest.dataSource = dataSource;
    }

    @Override
    protected DataSource getDataSource() {
        return dataSource;
    }

    @Override
    protected void dropSchema() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            final PreparedStatement statement =
                    connection.prepareStatement("DROP SCHEMA IF EXISTS unit10dbtest;");
            statement.execute();
        }
    }
}
