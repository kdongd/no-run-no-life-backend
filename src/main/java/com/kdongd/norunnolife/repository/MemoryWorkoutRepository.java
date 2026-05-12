package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryWorkoutRepository {

    private final Map<Long, Workout> store = new HashMap<>();
    private Long sequence = 0L;

    public Workout save(Workout workout) {
        workout.setId(++sequence);
        store.put(workout.getId(), workout);
        return workout;
    }

    public List<Workout> findAll() {
        return new ArrayList<>(store.values());
    }
}