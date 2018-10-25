package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;


@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_1.getId(), USER_ID);
        assertThat(meal).isEqualToComparingFieldByField(USER_MEAL_1);
        Meal meal2 = service.get(ADMIN_MEAL_1.getId(), ADMIN_ID);
        assertThat(meal2).isEqualToComparingFieldByField(ADMIN_MEAL_1);
    }
    @Test(expected = NotFoundException.class)
    public void getAnotherMeal() {
        Meal meal = service.get(USER_MEAL_1.getId(), ADMIN_ID);
        Meal meal2 = service.get(ADMIN_MEAL_1.getId(), USER_ID);

    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_1.getId(), USER_ID);
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_MEAL_3, USER_MEAL_2));
        service.delete(ADMIN_MEAL_2.getId(), ADMIN_ID);
        assertThat(service.getAll(ADMIN_ID)).isEqualTo(Arrays.asList(ADMIN_MEAL_3, ADMIN_MEAL_1));
    }


    @Test (expected = NotFoundException.class)
    public void deleteAnotherMeal() {
        service.delete(USER_MEAL_1.getId(), ADMIN_ID);
        service.delete(ADMIN_MEAL_2.getId(), USER_ID);

    }

    @Test
    public void getBetweenDateTimes() {
       List<Meal> userList= service.getBetweenDateTimes(LocalDateTime.of(2018,10,24,13,0, 0),
                LocalDateTime.of(2018,10,24,15,0,0) ,
                USER_ID);
       assertThat(userList).isEqualTo(Arrays.asList(USER_MEAL_2));
        List<Meal> userList2= service.getBetweenDateTimes(LocalDateTime.of(2018,10,24,13,0,0),
                LocalDateTime.of(2018,10,24,13,33,0) ,
                USER_ID);
        assertThat(userList2).isEqualTo(Collections.EMPTY_LIST);
    }

    @Test
    public void getAll() {
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_MEAL_3, USER_MEAL_2, USER_MEAL_1));
        assertThat(service.getAll(ADMIN_ID)).isEqualTo(Arrays.asList(ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1));
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDescription("Update: завтрак");
        updated.setCalories(333);
        service.update(updated, USER_ID);
        assertThat(service.get(USER_MEAL_1.getId(), USER_ID)).isEqualToComparingFieldByField(updated);
        Meal updated2 = new Meal(ADMIN_MEAL_3);
        updated2.setDescription("Update: ужин");
        updated2.setCalories(333);
        service.update(updated2, ADMIN_ID);
        assertThat(service.get(ADMIN_MEAL_3.getId(), ADMIN_ID)).isEqualToComparingFieldByField(updated2);
    }


    @Test (expected = NotFoundException.class)
    public void updateAnotherMeal() {
        Meal updated = new Meal(USER_MEAL_1);
        updated.setDescription("Update: завтрак");
        updated.setCalories(333);
        service.update(updated, ADMIN_ID);


        Meal updated2 = new Meal(ADMIN_MEAL_3);
        updated2.setDescription("Update: ужин");
        updated2.setCalories(333);
        service.update(updated2, USER_ID);
    }

    @Test
    public void create() {
        Meal newUserMeal = new Meal(LocalDateTime.of(2018,10,24, 17, 0,0),"Ланч", 111);
        Meal createdUserMeal = service.create(newUserMeal, USER_ID);
        newUserMeal.setId(createdUserMeal.getId());
        assertThat(service.getAll(USER_ID)).isEqualTo(Arrays.asList(USER_MEAL_3, newUserMeal,USER_MEAL_2, USER_MEAL_1));

        Meal newAdminMeal = new Meal(LocalDateTime.of(2018,10,25, 17, 0,0),"Ланч", 111);
        Meal createdAdminMeal = service.create(newAdminMeal, ADMIN_ID);
        newAdminMeal.setId(createdAdminMeal.getId());
        assertThat(service.getAll(ADMIN_ID)).isEqualTo(Arrays.asList(ADMIN_MEAL_3, newAdminMeal,ADMIN_MEAL_2, ADMIN_MEAL_1));

    }
}