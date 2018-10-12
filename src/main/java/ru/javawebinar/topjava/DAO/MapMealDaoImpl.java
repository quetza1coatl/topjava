package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealDaoImpl implements MealDao<Meal> {
    private static final ConcurrentHashMap<Integer, Meal> map = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    {
        save(new Meal(LocalDateTime.of(2018, Month.OCTOBER, 1, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2018, Month.OCTOBER, 1, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2018, Month.OCTOBER, 1, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2018, Month.OCTOBER, 2, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2018, Month.OCTOBER, 2, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2018, Month.OCTOBER, 2, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> mealList = new ArrayList<>();
        map.forEach((k, v) -> mealList.add(v));

        return mealList;
    }

    @Override
    public Meal findById(int id) {
        return map.get(id);
    }

    @Override
    public void save(Meal obj) {
        Meal meal = new Meal(obj.getDateTime(), obj.getDescription(), obj.getCalories());
        meal.setId(counter.incrementAndGet());
        map.put(counter.get(), meal);

    }

    @Override
    public void update(Meal obj) {
        map.put(obj.getId(), obj);
    }

    @Override
    public void deleteById(int id) {
        map.remove(id);
    }
}
