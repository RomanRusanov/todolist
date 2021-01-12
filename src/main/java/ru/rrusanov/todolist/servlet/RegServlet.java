package ru.rrusanov.todolist.servlet;


import ru.rrusanov.todolist.model.Role;
import ru.rrusanov.todolist.model.User;
import ru.rrusanov.todolist.store.Hibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 19.11.2020
 * email roman9628@gmail.com
 * The class .
 */
public class RegServlet extends HttpServlet{
    /**
     * The preprocess.
     * @param req Request.
     * @param resp Response.
     * @throws javax.servlet.ServletException ServletException.
     * @throws java.io.IOException IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/reg.jsp").forward(req, resp);
    }
    /**
     * The postprocess.
     * @param req Request.
     * @param resp Response.
     * @throws javax.servlet.ServletException ServletException.
     * @throws java.io.IOException IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Role role = Hibernate.instOf().createModel(Role.of(req.getParameter("role")));
        User user = Hibernate.instOf().createModel(User.of(
                req.getParameter("login"), role)
        );
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

}