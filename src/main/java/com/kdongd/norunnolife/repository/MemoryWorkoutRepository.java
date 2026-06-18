package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Qualifier("memoryWorkoutRepository")
public class MemoryWorkoutRepository implements WorkoutRepository {

    private final Map<Long, Workout> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Workout save(Workout workout) {
        if (workout.getId() == null) {
            Long id = sequence.incrementAndGet();
            Workout saved = Workout.withId(id, workout.getType(), workout.getDurationMinutes(), workout.getMemo(), workout.getWorkoutDateTime());
            store.put(id, saved);
            return saved;
        }
        store.put(workout.getId(), workout);
        return workout;
    }

    @Override
    public Optional<Workout> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Workout> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(Workout workout) {
        store.remove(workout.getId());
    }
}