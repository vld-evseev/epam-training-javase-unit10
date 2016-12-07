package training.tasks.unit10.dao.pg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.tasks.unit10.dao.UserDao;
import training.tasks.unit10.model.User;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PgUserDao implements UserDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(PgUserDao.class);

    private final DataSource dataSource;

    @Inject
    public PgUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> getByUserName(String login) {
        Optional<User> result = Optional.empty();

        try (Connection connection = dataSource.getConnection()) {
            final PreparedStatement query = connection.prepareStatement(
                    "SELECT id, login, firstName, lastName, email, passwordHash" +
                            " FROM unit10db.user" +
                            " WHERE login = ?;"
            );
            query.setString(1, login);
            final ResultSet resultSet = query.executeQuery();

            if (resultSet.next()) {
                final User user = User.builder()
                        .id(resultSet.getInt("id"))
                        .userName(resultSet.getString("login"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .email(resultSet.getString("email"))
                        .passwordHash(resultSet.getString("passwordHash"))
                        .build();
                result = Optional.of(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return result;
    }

    @Override
    public int add(User user) {
        int result = -1;
        try (Connection connection = dataSource.getConnection()) {
            final String[] returnColumns = {"id"};
            final PreparedStatement query = connection.prepareStatement(
                    "INSERT INTO unit10db.user(id, login, firstName, lastName, email, passwordHash)" +
                            " VALUES (nextval('unit10db.user_seq')," +
                            " ?, ?, ?, ?, ?);",
                    returnColumns
            );
            query.setString(1, user.getUserName());
            query.setString(2, user.getFirstName());
            query.setString(3, user.getLastName());
            query.setString(4, user.getEmail());
            query.setString(5, user.getPasswordHash());
            query.executeUpdate();
            final ResultSet generatedKeys = query.getGeneratedKeys();
            if (generatedKeys.next()) {
                result = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return result;
    }

}
