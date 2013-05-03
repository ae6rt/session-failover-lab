package org.petrovic;

import com.thoughtworks.xstream.XStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        HttpSession session = httpServletRequest.getSession();

        // "key" is some session attribute we know is in the session after a user logs in.
        if (session.getAttribute("key") != null) {
            String xml = new XStream().toXML(session);
            httpServletResponse.setHeader("X-session-size", Integer.toString(xml.length()));
            System.out.printf("session: %s\n", xml);
        }
        chain.doFilter(req, httpServletResponse);
    }

}
