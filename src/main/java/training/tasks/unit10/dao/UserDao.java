package training.tasks.unit10.dao;

import training.tasks.unit10.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getByUserName(String login);

    int add(User user);

}
