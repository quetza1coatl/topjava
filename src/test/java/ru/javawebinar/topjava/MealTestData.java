package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {
    public static final Meal USER_MEAL_1 = new Meal(100002, LocalDateTime.of(2018,10,24, 10, 0,0),"Завтрак", 140);
    public static final Meal USER_MEAL_2 = new Meal(100003, LocalDateTime.of(2018,10,24, 14, 0,0),"Обед", 500);
    public static final Meal USER_MEAL_3 = new Meal(100004, LocalDateTime.of(2018,10,24, 19, 0,0),"Ужин", 700);
    public static final Meal ADMIN_MEAL_1 = new Meal(100005, LocalDateTime.of(2018,10,25, 10, 0,0),"Завтрак", 100);
    public static final Meal ADMIN_MEAL_2 = new Meal(100006, LocalDateTime.of(2018,10,25, 14, 0,0),"Обед", 500);
    public static final Meal ADMIN_MEAL_3 = new Meal(100007, LocalDateTime.of(2018,10,25, 19, 0,0),"Ужин", 502);
}
