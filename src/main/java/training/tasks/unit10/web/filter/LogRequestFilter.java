package training.tasks.unit10.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@WebFilter("/*")
public class LogRequestFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final Map<String, String[]> parameters = req.getParameterMap();

        final Set<Map.Entry<String, String[]>> entries = parameters.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            LOGGER.debug("Request params [" + entry.getKey() + " : " + Arrays.toString(entry.getValue()) + "]");
        }

        final Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                LOGGER.debug("Cookies from " + req.getRemoteAddr() + " : " + cookie.getName() + ", " + cookie.getValue());
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
