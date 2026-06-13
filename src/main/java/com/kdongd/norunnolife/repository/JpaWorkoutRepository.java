package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaWorkoutRepository implements WorkoutRepository {

    private final WorkoutJpaRepository workoutJpaRepository;

    @Override
    public Workout save(Workout workout) {
        return workoutJpaRepository.save(workout);
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return workoutJpaRepository.findById(id);
    }

    @Override
    public List<Workout> findAll() {
        return workoutJpaRepository.findAll();
    }

    @Override
    public void delete(Workout workout) {
        workoutJpaRepository.delete(workout);
    }
}