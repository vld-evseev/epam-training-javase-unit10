package training.tasks.unit10.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoggedInFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;
        final HttpSession session = request.getSession(false);
        final boolean isLoggedIn = session != null && session.getAttribute("user") != null;

        final String loginPage = request.getContextPath() + "/login";

        if (isLoggedIn) {
            response.sendRedirect(loginPage);
            return;
        }
        /*final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;

        final HttpSession session = request.getSession();
        final Boolean authorised = (Boolean) session.getAttribute("authorised");

        if (authorised == null || !authorised) {
            session.setAttribute("authorised", false);
        }*/

        chain.doFilter(req, resp);
    }


}
