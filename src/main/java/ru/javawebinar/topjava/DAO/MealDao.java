package ru.javawebinar.topjava.DAO;

import java.util.List;

public interface MealDao<T> {

    List<T> findAll();

    T findById(int id);

    void save(T obj);

    void update(T obj);

    void deleteById(int id);

}
