package training.tasks.unit10.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.tasks.unit10.dao.UserDao;
import training.tasks.unit10.model.Credentials;
import training.tasks.unit10.model.User;
import training.tasks.unit10.service.SecurityService;
import training.tasks.unit10.service.UserService;

import javax.inject.Inject;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;
    private final SecurityService securityService;

    @Inject
    public UserServiceImpl(UserDao userDao, SecurityService securityService) {
        this.userDao = userDao;
        this.securityService = securityService;
    }

    @Override
    public Optional<User> getByCredentials(Credentials credentials) {
        final Optional<User> userOptional = userDao.getByUserName(credentials.getLogin());
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }

        final User user = userOptional.get();

        LOGGER.debug("validating: pass[{}, {}], hash[{}]",
                credentials.getPassword(),
                securityService.encrypt(credentials.getPassword()),
                user.getPasswordHash());

        final boolean passwordValidated = securityService.validate(credentials.getPassword(), user.getPasswordHash());
        if (!passwordValidated) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public long add(User user) {
        return userDao.add(user);
    }
}
