package ru.rrusanov.todolist.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import ru.rrusanov.todolist.model.Item;
import ru.rrusanov.todolist.model.Role;
import ru.rrusanov.todolist.model.User;
import ru.rrusanov.todolist.store.Hibernate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 30.12.2020
 * email roman9628@gmail.com
 * The class describe servlet when user add new todo row.
 */
public class NewTodo extends HttpServlet {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NewTodo.class.getName());
    /**
     * The marker for logger.
     */
    private static final Marker MARKER = MarkerFactory.getMarker("NewTodo");
    /**
     * The preprocess.
     * @param req Request.
     * @param resp Response.
     * @throws ServletException ServletException.
     * @throws IOException IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        String description = data.get("description").getAsString();
        Long currentDateTime = data.get("currentDateTime").getAsLong();
        User user = Hibernate.instOf()
                .findUserByLogin(data.get("userName").getAsString());
        Item item = new Item(user, description, new Timestamp(currentDateTime), false);
        LOG.info(MARKER, "Server create item: {}", item);
        Hibernate.instOf().createModel(item);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Item> list = Hibernate.instOf().findAllItems();
        Gson jsonObject = new Gson().newBuilder()
                .setPrettyPrinting()
                .create();
        String json = jsonObject.toJson(list);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), false, StandardCharsets.UTF_8);
        writer.print(json);
        writer.flush();
    }
}