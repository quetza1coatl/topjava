package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.TestUtil.readFromJson;


class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';
    @Autowired
    MealService service;

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + MEAL6.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(SecurityUtil.authUserId()), MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void testDeleteWithNotFoundException(){
        NotFoundException e = assertThrows(NotFoundException.class, () ->
                service.get(ADMIN_MEAL1.getId(), SecurityUtil.authUserId()));
        assertEquals(e.getMessage(),"Not found entity with id=" + ADMIN_MEAL1.getId() );

    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }


    @Test
    void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1.getId(), MEAL1.getDateTime(),MEAL1.getDescription(),MEAL1.getCalories());
        updated.setCalories(100);
        updated.setDescription("Updated");
        mockMvc.perform(put(REST_URL + "/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(MEAL1_ID, SecurityUtil.authUserId()), updated);
    }

    @Test
    void testCreate() throws Exception {
        Meal created = new Meal(null, of(2018, Month.MAY, 31, 20, 0), "Created", 150);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());
        Meal returned = readFromJson(action, Meal.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(SecurityUtil.authUserId()),created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
        assertMatch(service.get(MEAL1_ID + 8,SecurityUtil.authUserId()) , created);

    }
    @Test
    void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/filter" + "?startDateTime=2015-05-31T10:15:30&endDateTime=2015-05-31T22:00:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(contentJson(MEAL6, MEAL5));
    }

}