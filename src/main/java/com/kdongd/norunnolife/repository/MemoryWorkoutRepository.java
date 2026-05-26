package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryWorkoutRepository {

    private final Map<Long, Workout> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public Workout save(Workout workout) {
        Long id = sequence.incrementAndGet();
        Workout saved = Workout.withId(id, workout.getType(), workout.getDurationMinutes(), workout.getMemo(), workout.getWorkoutDateTime());
        store.put(id, saved);
        return saved;
    }

    public List<Workout> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Workout> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}