package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(meal.isNew()){
            User user = em.getReference(User.class, userId);
            meal.setUser(user);
            em.persist(meal);
            return meal;
        }
        else{
            Meal mealFromDB = em.find(Meal.class, meal.getId());
            if(mealFromDB.getUser().getId() == userId){
               meal.setUser(em.find(User.class, userId));
               return em.merge(meal);
            }



        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Query query = em.createQuery("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId");
        return query.setParameter("id",  id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;

    }

    @Override
    public Meal get(int id, int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.id =:id");
        List<Meal> list = query
                .setParameter("userId", userId)
                .setParameter("id", id)
                .getResultList();
        return DataAccessUtils.singleResult(list);

    }

    @Override
    public List<Meal> getAll(int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime desc");
        return query.setParameter("userId", userId).getResultList();

    }

    @Override
    @SuppressWarnings("all")
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Query query = em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime desc");
        return query
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}