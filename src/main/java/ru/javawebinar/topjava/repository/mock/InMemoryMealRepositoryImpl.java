package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {

        MealsUtil.MEALS.forEach(e -> this.save(e, SecurityUtil.authUserId()));
    }


    @Override
    public Meal save(Meal meal, int userID) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }

        if(meal.getUserID() == userID)
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        else return null;

    }

    @Override
    public boolean delete(int id, int userID) {
        Meal meal = repository.get(id);
        if((meal != null) && (meal.getUserID() == userID))
        return repository.remove(id) != null;
        else return false;
    }

    @Override
    public Meal get(int id,int userID ) {
        Meal meal = repository.get(id);
        if((meal != null) && (meal.getUserID() == userID))
        return repository.get(id);
        else return null;
    }

    @Override
    public List<Meal> getAll(int userID) {

        return repository.values()
                .stream()
                .filter(meal -> meal.getUserID() == userID)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }
}

