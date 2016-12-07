package training.tasks.unit10.db;

import com.google.inject.Provider;
import org.postgresql.ds.PGPoolingDataSource;

import javax.inject.Inject;
import javax.sql.DataSource;

public class PostgresDataSourceProvider implements Provider<DataSource> {

    private final PGConfig pgConfig;

    @Inject
    public PostgresDataSourceProvider(PGConfig pgConfig) {
        this.pgConfig = pgConfig;
    }

    @Override
    public DataSource get() {
        final PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setServerName(pgConfig.getHost());
        dataSource.setPortNumber(pgConfig.getPort());
        dataSource.setDatabaseName(pgConfig.getDbName());
        dataSource.setUser(pgConfig.getUser());
        dataSource.setPassword(pgConfig.getPassword());
        dataSource.setInitialConnections(2);
        dataSource.setMaxConnections(4);
        return dataSource;
    }

}
