package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println("*********");
            mealRestController.getAll().forEach(System.out::println);
            System.out.println("*********");
            mealRestController.delete(3);
            mealRestController.getAll().forEach(System.out::println);
            mealRestController.update(new Meal(1, LocalDateTime.now(), "Dinner", 158), 1);
            System.out.println("*********");
            mealRestController.getAll().forEach(System.out::println);
            mealRestController.create(new Meal( LocalDateTime.now(), "New", 2215));
            System.out.println("*********");
            mealRestController.getAll().forEach(System.out::println);
            System.out.println("*********");
            System.out.println(mealRestController.get(5));
            System.out.println("*********");
            System.out.println(mealRestController.get(3));



        }
    }
}
