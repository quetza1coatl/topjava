package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MealService service;
    @Autowired
    public MealRestController (MealService service){this.service = service;}

    public List<MealWithExceed> getAll() {
        log.info("getAll (DTO)");
        List<Meal> mealList = service.getAll(authUserId());
        return getWithExceeded(mealList, authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(HttpServletRequest request) {
        log.info("getFiltered");

        LocalDate fromDate = request.getParameter("fromDate").isEmpty() ?
                null : LocalDate.parse(request.getParameter("fromDate"));

        LocalDate toDate = request.getParameter("toDate").isEmpty() ?
                null : LocalDate.parse(request.getParameter("toDate"));

        LocalTime fromTime = request.getParameter("fromTime").isEmpty() ?
                null : LocalTime.parse(request.getParameter("fromTime"));

        LocalTime toTime = request.getParameter("toTime").isEmpty() ?
                null : LocalTime.parse(request.getParameter("toTime"));

        List<Meal> mealList = service.getFiltered(authUserId(),
                fromDate != null ? fromDate : LocalDate.MIN,
                toDate != null ? toDate : LocalDate.MAX);

        List<MealWithExceed> mealWithExceeds = getWithExceeded(mealList, authUserCaloriesPerDay());
        return mealWithExceeds.stream()
                .filter(mealWithExceed -> isBetween(mealWithExceed.getTime(),
                        fromTime != null ? fromTime : LocalTime.MIN,
                        toTime != null ? toTime : LocalTime.MAX))
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }



}