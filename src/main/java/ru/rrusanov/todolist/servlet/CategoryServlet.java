package ru.rrusanov.todolist.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import ru.rrusanov.todolist.model.Category;
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
 * @since 16.01.2021
 * email roman9628@gmail.com
 * The class describe servlet what send all categories.
 */
public class CategoryServlet extends HttpServlet {
    /**
     * The instance with logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CategoryServlet.class.getName());
    /**
     * The marker for logger.
     */
    private static final Marker MARKER = MarkerFactory.getMarker("CategoryServlet");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info(MARKER, "Server receive ajax to get all categories.");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        List<Category> list = Hibernate.instOf().getAllCategories();
        Gson jsonObject = new Gson().newBuilder()
                .setPrettyPrinting()
                .create();
        String json = jsonObject.toJson(list);
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), false, StandardCharsets.UTF_8);
        writer.print(json);
        writer.flush();
    }
}