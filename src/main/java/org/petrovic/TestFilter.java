package org.petrovic;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestFilter implements Filter {

    private String cached;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (cached == null) {
            Wrapper wrapper = new Wrapper((HttpServletResponse) resp);
            chain.doFilter(req, wrapper);
            cached = wrapper.sw.toString();
            System.out.println("cache this: " + cached);
        }
        resp.getWriter().write(cached);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    private class Wrapper extends HttpServletResponseWrapper {
        final StringWriter sw = new StringWriter();
        private final PrintWriter writer = new PrintWriter(sw);

        public Wrapper(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

    }
}
