package com.kdongd.norunnolife.service;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.dto.WorkoutRequest;
import com.kdongd.norunnolife.dto.WorkoutResponse;
import com.kdongd.norunnolife.repository.MemoryWorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final MemoryWorkoutRepository workoutRepository;

    public WorkoutResponse createWorkout(WorkoutRequest request) {
        Workout workout = new Workout(
                request.type(),
                request.durationMinutes(),
                request.memo(),
                request.workoutDateTime()
        );
        Workout saved = workoutRepository.save(workout);
        return toResponse(saved);
    }

    public List<WorkoutResponse> getWorkouts() {
        return workoutRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private WorkoutResponse toResponse(Workout workout) {
        return new WorkoutResponse(
                workout.getId(),
                workout.getType(),
                workout.getDurationMinutes(),
                workout.getMemo(),
                workout.getWorkoutDateTime()
        );
    }

    public WorkoutResponse getWorkout(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("운동 기록을 찾을 수 없습니다."));
        return toResponse(workout);
    }
}