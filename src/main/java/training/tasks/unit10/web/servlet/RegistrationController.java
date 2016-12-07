package training.tasks.unit10.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import training.tasks.unit10.model.Credentials;
import training.tasks.unit10.model.User;
import training.tasks.unit10.service.SecurityService;
import training.tasks.unit10.service.UserService;
import training.tasks.unit10.web.servlet.model.FieldValidation;
import training.tasks.unit10.web.servlet.model.FormValidation;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class RegistrationController extends HttpServlet {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String AUTHORISED = "authorised";
    private static final String REGISTRATION_PAGE = "/WEB-INF/jsp/registration.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;
    private final SecurityService securityService;

    @Inject
    RegistrationController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("creds", Credentials.builder().build());
        req.setAttribute("user", User.builder().build());
        req.getRequestDispatcher(REGISTRATION_PAGE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Credentials credentials = Credentials.builder()
                .login(req.getParameter(LOGIN))
                .password(req.getParameter(PASSWORD))
                .build();

        final User user = User.builder()
                .userName(req.getParameter(LOGIN))
                .firstName(req.getParameter(FIRST_NAME))
                .lastName(req.getParameter(LAST_NAME))
                .email(req.getParameter(EMAIL))
                .passwordHash(securityService.encrypt(credentials.getPassword()))
                .build();

        final FormValidation credentialValidation = validateCredentials(credentials);
        final FormValidation userValidation = validateRegistrationForm(user);

        if (!credentialValidation.isValid() || !userValidation.isValid()) {
            req.setAttribute("credsValidation", credentialValidation);
            req.setAttribute("userValidation", userValidation);
            req.setAttribute("creds", credentials);
            req.setAttribute("user", user);
            req.getRequestDispatcher(REGISTRATION_PAGE)
                    .forward(req, resp);
            return;
        }

        final String encrypt = securityService.encrypt(credentials.getPassword());
        LOGGER.debug(encrypt);
        long id = userService.add(user);

        if (id == -1) {
            userValidation.getErrors().put("registration_failed", true);
            req.setAttribute("credsValidation", credentialValidation);
            req.setAttribute("userValidation", userValidation);
            req.setAttribute("creds", credentials);
            req.setAttribute("user", user);
            req.getRequestDispatcher(REGISTRATION_PAGE)
                    .forward(req, resp);
            return;
        }

        req.getSession(true).setAttribute(AUTHORISED, true);
        req.getSession(true).setAttribute("user", user);

        resp.sendRedirect(req.getContextPath() + "/user/profile");
    }

    static FormValidation validateRegistrationForm(User user) {
        final FormValidation validation = new FormValidation();

        checkFieldNotFilled(validation, LOGIN, user.getUserName());
        checkFieldNotFilled(validation, FIRST_NAME, user.getFirstName());
        checkFieldNotFilled(validation, LAST_NAME, user.getLastName());
        checkFieldNotFilled(validation, EMAIL, user.getEmail());

        return validation;
    }

    static FormValidation validateCredentials(Credentials credentials) {
        final FormValidation validation = new FormValidation();

        checkFieldNotFilled(validation, LOGIN, credentials.getLogin());
        checkFieldNotFilled(validation, PASSWORD, credentials.getPassword());

        return validation;
    }

    static void checkFieldNotFilled(FormValidation validation, String fieldName, String field) {
        if (field == null || field.isEmpty()) {
            validation.getFields().put(
                    fieldName,
                    FieldValidation.builder().isEmptyField(true).build()
            );
        }
    }
}
