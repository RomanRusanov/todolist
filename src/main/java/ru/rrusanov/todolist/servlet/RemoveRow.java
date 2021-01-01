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

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 30.12.2020
 * email roman9628@gmail.com
 * The class describe servlet when user remove one todo row.
 */
public class RemoveRow extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        JsonObject data = new Gson().fromJson(req.getReader(), JsonObject.class);
        String item_id = data.get("item_id").getAsString();
        LOG.info("Server receive id to remove: " + item_id);
        if (Hibernate.instOf().delete(item_id)) {
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Gson jsonObject = new Gson().newBuilder()
                    .setPrettyPrinting()
                    .create();
            Item item = new Item("", new Timestamp(0L), false);
            item.setItem_id(Integer.parseInt(item_id));
            String json = jsonObject.toJson(item);
            System.out.println(json);
            PrintWriter writer = new PrintWriter(resp.getOutputStream(), false, StandardCharsets.UTF_8);
            writer.print(json);
            writer.flush();
        } else {
            resp.setStatus(412);
        }
    }
}