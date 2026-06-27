package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("jpaWorkoutRepository")
public class JpaWorkoutRepository implements WorkoutRepository {

    private final EntityManager em;

    @Override
    public Workout save(Workout workout) {
        em.persist(workout);
        return workout;
    }

    @Override
    public Optional<Workout> findById(Long id) {
        List<Workout> result = em.createQuery(
                        "select w from Workout w left join fetch w.details where w.id = :id", Workout.class)
                .setParameter("id", id)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<Workout> findAll() {
        return em.createQuery(
                        "select distinct w from Workout w left join fetch w.details", Workout.class)
                .getResultList();
    }

    @Override
    public void delete(Workout workout) {
        em.remove(workout);
    }
}