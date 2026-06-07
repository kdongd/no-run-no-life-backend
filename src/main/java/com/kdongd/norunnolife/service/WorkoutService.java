package com.kdongd.norunnolife.service;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.dto.WorkoutRequest;
import com.kdongd.norunnolife.dto.WorkoutResponse;
import com.kdongd.norunnolife.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    @Transactional
    public WorkoutResponse createWorkout(WorkoutRequest request) {
        Workout workout = Workout.create(
                request.type(),
                request.durationMinutes(),
                request.memo(),
                request.workoutDateTime()
        );
        return WorkoutResponse.from(workoutRepository.save(workout));
    }

    @Transactional
    public WorkoutResponse updateWorkout(Long id, WorkoutRequest request) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("운동 기록을 찾을 수 없습니다."));
        workout.update(request.type(), request.durationMinutes(), request.memo(), request.workoutDateTime());
        return WorkoutResponse.from(workout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new NoSuchElementException("운동 기록을 찾을 수 없습니다.");
        }
        workoutRepository.deleteById(id);
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