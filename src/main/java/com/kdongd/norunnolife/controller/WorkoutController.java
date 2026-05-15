package com.kdongd.norunnolife.controller;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping("/workouts")
    public List<Workout> getWorkouts() {
        return workoutService.getWorkouts();
    }

    @PostMapping("/workouts")
    public Workout createWorkout(@Valid @RequestBody Workout workout) {
        return workoutService.createWorkout(workout);
    }

}