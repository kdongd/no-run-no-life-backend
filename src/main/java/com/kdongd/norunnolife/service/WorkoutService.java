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
        Workout workout = Workout.create(
                request.type(),
                request.durationMinutes(),
                request.memo(),
                request.workoutDateTime()
        );
        return WorkoutResponse.from(workoutRepository.save(workout));
    }

    public List<WorkoutResponse> getWorkouts() {
        return workoutRepository.findAll().stream()
                .map(WorkoutResponse::from)
                .toList();
    }

    public WorkoutResponse getWorkout(Long id) {
        return workoutRepository.findById(id)
                .map(WorkoutResponse::from)
                .orElseThrow(() -> new NoSuchElementException("운동 기록을 찾을 수 없습니다."));
    }
}