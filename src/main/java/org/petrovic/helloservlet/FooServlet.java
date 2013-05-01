package org.petrovic.helloservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class FooServlet extends HttpServlet {

    String key = "key";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object value = session.getAttribute(key);
        if (value == null) {
            String marker = request.getParameter(key);
            if (marker == null) {
                System.out.printf("no marker, no session attribute. returning.\n");
                return;
            }
            System.out.printf("setting new marker on the session: %s\n", marker);
            session.setAttribute(key, marker);
            value = marker;
        } else {
            System.out.printf("found a marker on the session: %s\n", value);
        }
        PrintWriter writer = response.getWriter();
        writer.print("session originated with marker " + value);
    }
}