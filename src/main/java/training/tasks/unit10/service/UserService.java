package training.tasks.unit10.service;


import training.tasks.unit10.model.Credentials;
import training.tasks.unit10.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getByCredentials(Credentials credentials);

    long add(User user);

}
