package ru.rrusanov.todolist.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import ru.rrusanov.todolist.model.User;
import ru.rrusanov.todolist.store.Hibernate;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 11.11.2020
 * email roman9628@gmail.com
 * The class describe servlet that authenticate users.
 */
public class AuthServlet extends HttpServlet {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NewTodo.class.getName());
    /**
     * The marker for logger.
     */
    private static final Marker MARKER = MarkerFactory.getMarker("Auth");
    /**
     * The postprocess.
     * @param req Request.
     * @param resp Response.
     * @throws javax.servlet.ServletException ServletException.
     * @throws java.io.IOException IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User userFromDB = Hibernate.instOf().findUserByLogin(login);
        if (!userFromDB.getName().equals("-1") || userFromDB.getName().equals(login)) {
            LOG.info(MARKER, "Server authenticate user: {}", userFromDB);
            HttpSession sc = req.getSession();
            sc.setAttribute("user", userFromDB);
            resp.addCookie(new Cookie("userName", userFromDB.getName()));
            resp.sendRedirect(req.getContextPath() + "/index.html");
        } else {
            LOG.info(MARKER, "Try to login not registered user.");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}