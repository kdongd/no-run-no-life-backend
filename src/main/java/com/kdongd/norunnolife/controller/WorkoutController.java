package com.kdongd.norunnolife.controller;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping("/workouts")
    public String getWorkouts(Model model) {
        List<Workout> workouts = workoutService.getWorkouts();
        model.addAttribute("workouts", workouts);
        return "workouts";
    }

    @GetMapping("/workouts/new")
    public String createForm(Model model) {
        model.addAttribute("workout", new Workout());
        return "workout-form";
    }

    @PostMapping("/workouts")
    public String createWorkout(@Valid @ModelAttribute Workout workout, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "workout-form";
        }
        workoutService.createWorkout(workout);
        return "redirect:/workouts";
    }
}