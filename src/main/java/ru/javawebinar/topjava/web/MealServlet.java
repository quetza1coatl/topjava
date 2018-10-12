package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MapMealDaoImpl;
import ru.javawebinar.topjava.DAO.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao<Meal> repo = new MapMealDaoImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            List<Meal> meals = repo.findAll();
            List<MealWithExceed> mealsWithExceed = MealsUtil.getWithExceeded(meals, 2000);
            req.setAttribute("mealsWithExceed", mealsWithExceed);
            req.setAttribute("formatter", TimeUtil.FORMATTER);

            req.getRequestDispatcher("/mealList.jsp").forward(req, resp);
        } else {

            switch (action) {
                case "add":
                    repo.save(getMealFromRequest(req));
                    resp.sendRedirect("meal");
                    break;
                case "delete":
                    repo.deleteById(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect("meal");
                    break;
                case "update":
                    Meal meal = repo.findById(Integer.parseInt(req.getParameter("id")));
                    req.setAttribute("meal", meal);
                    req.getRequestDispatcher("/mealUpdate.jsp").forward(req, resp);
                    break;

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal mealToUpdate = getMealFromRequest(req);
        mealToUpdate.setId(Integer.parseInt(req.getParameter("id")));
        repo.update(mealToUpdate);
        resp.sendRedirect("meal");
    }


    private Meal getMealFromRequest(HttpServletRequest req) {
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String date = req.getParameter("date");
        String time = req.getParameter("time");
        LocalDateTime dateTime = LocalDateTime.parse(date + "T" + time);
        return new Meal(dateTime, description, calories);

    }
}
