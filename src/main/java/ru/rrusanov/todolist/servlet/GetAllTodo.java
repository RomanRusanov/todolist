package ru.rrusanov.todolist.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rrusanov.todolist.model.Item;
import ru.rrusanov.todolist.store.Hibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 01.01.2021
 * email roman9628@gmail.com
 * The class describe servlet when request all existed rows todo.
 */
public class GetAllTodo extends HttpServlet {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NewTodo.class.getName());

    /**
     * The preprocess.
     * @param req Request.
     * @param resp Response.
     * @throws javax.servlet.ServletException ServletException.
     * @throws java.io.IOException IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Server receive ajax to get all todo items.");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Item> list = Hibernate.instOf().findAll();
        Gson jsonObject = new Gson().newBuilder()
                .setPrettyPrinting()
                .create();
        String json = jsonObject.toJson(list);
        System.out.println(json);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), false, StandardCharsets.UTF_8);
        writer.print(json);
        writer.flush();
    }
}