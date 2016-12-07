package training.tasks.unit10.db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PostgresManager implements DbManager {

    private final static Logger LOGGER = LoggerFactory.getLogger(PostgresManager.class);

    private final Flyway flyway;
    private final DataSource dataSource;

    @Inject
    public PostgresManager(DataSource dataSource) {
        this.dataSource = dataSource;
        flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setLocations("db.init");
    }

    @Override
    public void create() {
        drop();
        flyway.migrate();
        LOGGER.debug("DB was initialized");
    }

    @Override
    public void drop() {
        try {
            try (Connection connection = dataSource.getConnection()) {
                final PreparedStatement statement =
                        connection.prepareStatement("DROP SCHEMA IF EXISTS unit10db CASCADE;");
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
