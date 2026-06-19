package com.kdongd.norunnolife.service;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.domain.WorkoutDetail;
import com.kdongd.norunnolife.dto.WorkoutDetailRequest;
import com.kdongd.norunnolife.dto.WorkoutRequest;
import com.kdongd.norunnolife.dto.WorkoutResponse;
import com.kdongd.norunnolife.exception.WorkoutNotFoundException;
import com.kdongd.norunnolife.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutService(@Qualifier("jpaWorkoutRepository") WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @Transactional
    public WorkoutResponse createWorkout(WorkoutRequest request) {
        Workout workout = Workout.create(
                request.type(),
                request.durationMinutes(),
                request.memo(),
                request.workoutDateTime()
        );

        if (request.details() != null) {
            for (WorkoutDetailRequest detailRequest : request.details()) {
                WorkoutDetail detail = WorkoutDetail.create(
                        workout,
                        detailRequest.sequence(),
                        detailRequest.label(),
                        detailRequest.durationSeconds(),
                        detailRequest.note()
                );
                workout.addDetail(detail);
            }
        }

        return WorkoutResponse.from(workoutRepository.save(workout));
    }

    @Transactional
    public WorkoutResponse updateWorkout(Long id, WorkoutRequest request) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(id));
        workout.update(request.type(), request.durationMinutes(), request.memo(), request.workoutDateTime());
        return WorkoutResponse.from(workout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new WorkoutNotFoundException(id));
        workoutRepository.delete(workout);
    }

    public List<WorkoutResponse> getWorkouts() {
        return workoutRepository.findAll().stream()
                .map(WorkoutResponse::from)
                .toList();
    }

    public WorkoutResponse getWorkout(Long id) {
        return workoutRepository.findById(id)
                .map(WorkoutResponse::from)
                .orElseThrow(() -> new WorkoutNotFoundException(id));
    }
}