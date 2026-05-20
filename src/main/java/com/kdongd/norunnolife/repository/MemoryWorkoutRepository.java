package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryWorkoutRepository {

    private final Map<Long, Workout> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    public Workout save(Workout workout) {
        workout.assignId(sequence.incrementAndGet());
        store.put(workout.getId(), workout);
        return workout;
    }

    public List<Workout> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Workout> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}