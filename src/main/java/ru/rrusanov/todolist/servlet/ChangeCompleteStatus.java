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

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 01.01.2021
 * email roman9628@gmail.com
 * The class describe servlet when user change status of row.
 */
public class ChangeCompleteStatus extends HttpServlet {
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
        Boolean status = data.get("complete").getAsBoolean();
        LOG.info("Server receive id to change status: " + item_id + "status :" + status);
        Item itemToChangeDoneStatus = Hibernate.instOf().findById(item_id);
        itemToChangeDoneStatus.setDone(!status);
        if (Hibernate.instOf().replace(item_id, itemToChangeDoneStatus)) {
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            Gson jsonObject = new Gson().newBuilder()
                    .setPrettyPrinting()
                    .create();
            String json = jsonObject.toJson(itemToChangeDoneStatus);
            PrintWriter writer = new PrintWriter(resp.getOutputStream(), false, StandardCharsets.UTF_8);
            writer.print(json);
            writer.flush();
        } else {
            resp.setStatus(412);
        }
    }
}