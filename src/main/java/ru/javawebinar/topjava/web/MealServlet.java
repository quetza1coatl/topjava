package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);



    private MealRestController controller;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
            controller = appCtx.getBean(MealRestController.class);

        } catch (BeansException e) {
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        if (appCtx != null)
            appCtx.close();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {

            case "selectUser":
                SecurityUtil.setAuthUserId(Integer.parseInt(request.getParameter("select")));
                response.sendRedirect("meals");
                break;

            case "filter":
                List<MealWithExceed> filteredList = controller.getFiltered(request);
                request.setAttribute("warning", filteredList.isEmpty() ? "We can't find any data with this parameters" : "");
                request.setAttribute("meals", filteredList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                String id = request.getParameter("id");
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories")));

                log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                if (meal.isNew())
                    controller.create(meal);
                else
                    controller.update(meal, meal.getId());

                response.sendRedirect("meals");
                break;

        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");


        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":

            default:
                log.info("getAll");
                List<MealWithExceed> list = controller.getAll();
                request.setAttribute("warning", list.isEmpty() ? "We can't find any data with this parameters" : "");
                request.setAttribute("meals", list);
                request.setAttribute("user", SecurityUtil.authUserId());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
