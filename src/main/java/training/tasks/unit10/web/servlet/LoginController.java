package training.tasks.unit10.web.servlet;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Singleton
public class LoginController extends HttpServlet {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";
    private static final String AUTHORISED = "authorised";

    private final UserService userService;
    private final SecurityService securityService;

    @Inject
    public LoginController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("data", Credentials.builder().build());
        req.getRequestDispatcher(LOGIN_PAGE)
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Credentials credentials = Credentials.builder()
                .login(req.getParameter(LOGIN))
                .password(req.getParameter(PASSWORD))
                .build();

        final FormValidation validation = validate(credentials);

        if (validation.isValid()) {
            final Optional<User> userOptional = userService.getByCredentials(credentials);
            if (!userOptional.isPresent()) {
                validation.getErrors().put("INVALID_CREDENTIALS", true);
            } else {
                final HttpSession session = req.getSession(true);
                session.setAttribute("user", userOptional.get());
            }
        }

        if (!validation.isValid()) {
            req.setAttribute("validation", validation);
            req.setAttribute("data", credentials);
            req.getSession(true).setAttribute(AUTHORISED, false);
            req.getRequestDispatcher(LOGIN_PAGE)
                    .forward(req, resp);
            return;
        }

        req.getSession(true).setAttribute(AUTHORISED, true);
        resp.sendRedirect(req.getContextPath() + "/user/profile");
    }

    static FormValidation validate(Credentials credentials) {
        final FormValidation validation = new FormValidation();

        if (credentials.getLogin() == null || credentials.getLogin().isEmpty()) {
            validation.getFields().put(
                    LOGIN,
                    FieldValidation.builder().isEmptyField(true).build()
            );
        }

        if (credentials.getPassword() == null || credentials.getPassword().isEmpty()) {
            validation.getFields().put(
                    PASSWORD,
                    FieldValidation.builder().isEmptyField(true).build()
            );
        }

        return validation;
    }
}
