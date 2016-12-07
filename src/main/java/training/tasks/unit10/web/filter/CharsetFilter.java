package training.tasks.unit10.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(CharsetFilter.class);
    private String encoding = "utf-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        final String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null) {
            encoding = encodingParam;
        }
        LOGGER.debug("Charset: {}", encoding);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
