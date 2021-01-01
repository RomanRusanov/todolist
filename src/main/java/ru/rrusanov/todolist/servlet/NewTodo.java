package ru.rrusanov.todolist.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
        Item item = new Item(description, new Timestamp(currentDateTime), false);
        LOG.info("Server create item: " + item);
        Hibernate.instOf().add(item);
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