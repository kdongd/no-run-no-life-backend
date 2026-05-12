package com.kdongd.norunnolife.service;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.repository.MemoryWorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final MemoryWorkoutRepository workoutRepository;

    public Workout createWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    public List<Workout> getWorkouts() {
        return workoutRepository.findAll();
    }
}