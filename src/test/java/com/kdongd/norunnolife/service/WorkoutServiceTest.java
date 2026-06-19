package com.kdongd.norunnolife.service;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.domain.WorkoutType;
import com.kdongd.norunnolife.dto.WorkoutDetailRequest;
import com.kdongd.norunnolife.dto.WorkoutRequest;
import com.kdongd.norunnolife.dto.WorkoutResponse;
import com.kdongd.norunnolife.exception.WorkoutNotFoundException;
import com.kdongd.norunnolife.repository.WorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    private final LocalDateTime now = LocalDateTime.now();

    private WorkoutService workoutService() {
        return new WorkoutService(workoutRepository);
    }

    @Test
    @DisplayName("운동 기록 저장 후 응답값 반환")
    void createWorkout_success() {
        WorkoutRequest request = new WorkoutRequest(WorkoutType.RUNNING, 30, "테스트 메모", now, null);
        Workout saved = Workout.create(WorkoutType.RUNNING, 30, "테스트 메모", now);
        given(workoutRepository.save(any())).willReturn(saved);

        WorkoutResponse response = workoutService().createWorkout(request);

        assertThat(response.type()).isEqualTo(WorkoutType.RUNNING);
        assertThat(response.durationMinutes()).isEqualTo(30);
        assertThat(response.memo()).isEqualTo("테스트 메모");
    }

    @Test
    @DisplayName("details 포함 운동 기록 저장")
    void createWorkout_withDetails() {
        List<WorkoutDetailRequest> details = List.of(
                new WorkoutDetailRequest(1, "1라운드", 180, "새도우")
        );
        WorkoutRequest request = new WorkoutRequest(WorkoutType.BOXING, 60, "메모", now, details);
        Workout saved = Workout.create(WorkoutType.BOXING, 60, "메모", now);
        given(workoutRepository.save(any())).willReturn(saved);

        WorkoutResponse response = workoutService().createWorkout(request);

        assertThat(response.type()).isEqualTo(WorkoutType.BOXING);
    }

    @Test
    @DisplayName("저장된 운동 기록이 없을 때 빈 리스트 반환")
    void getWorkouts_empty() {
        given(workoutRepository.findAll()).willReturn(List.of());

        List<WorkoutResponse> result = workoutService().getWorkouts();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("저장된 운동 기록 전체 반환")
    void getWorkouts_multiple() {
        List<Workout> workouts = List.of(
                Workout.create(WorkoutType.RUNNING, 30, "메모1", now),
                Workout.create(WorkoutType.BOXING, 60, "메모2", now)
        );
        given(workoutRepository.findAll()).willReturn(workouts);

        List<WorkoutResponse> result = workoutService().getWorkouts();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("존재하는 id 조회 성공")
    void getWorkout_success() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);
        given(workoutRepository.findById(1L)).willReturn(Optional.of(workout));

        WorkoutResponse response = workoutService().getWorkout(1L);

        assertThat(response.type()).isEqualTo(WorkoutType.RUNNING);
    }

    @Test
    @DisplayName("존재하지 않는 id 조회 시 WorkoutNotFoundException 발생")
    void getWorkout_notFound() {
        given(workoutRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService().getWorkout(999L))
                .isInstanceOf(WorkoutNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 id 삭제 시 WorkoutNotFoundException 발생")
    void deleteWorkout_notFound() {
        given(workoutRepository.findById(999L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService().deleteWorkout(999L))
                .isInstanceOf(WorkoutNotFoundException.class);
    }

    @Test
    @DisplayName("존재하는 id 삭제 성공")
    void deleteWorkout_success() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);
        given(workoutRepository.findById(1L)).willReturn(Optional.of(workout));

        assertThatCode(() -> workoutService().deleteWorkout(1L))
                .doesNotThrowAnyException();
    }
}