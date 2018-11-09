package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_DATE_DESC = new Sort(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudRepository;
    private final CrudUserRepository crudUserRepository;
    @Autowired
    public DataJpaMealRepositoryImpl(CrudMealRepository crudRepository, CrudUserRepository crudUserRepository){
        this.crudRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;

    }


    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        } else{
            meal.setUser(crudUserRepository.getOne(userId));
            return crudRepository.save(meal);
        }

    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id,userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);

        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll(SORT_DATE_DESC).stream()
                .filter(p -> p.getUser().getId() == userId)
                .collect(Collectors.toList());

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.getBeetween(startDate, endDate, userId);
    }
}
