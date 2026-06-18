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
        if (workout.getId() == null) {
            em.persist(workout);
            return workout;
        }
        return em.merge(workout);
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return Optional.ofNullable(em.find(Workout.class, id));
    }

    @Override
    public List<Workout> findAll() {
        return em.createQuery("select w from Workout w", Workout.class).getResultList();
    }

    @Override
    public void delete(Workout workout) {
        em.remove(em.contains(workout) ? workout : em.merge(workout));
    }
}