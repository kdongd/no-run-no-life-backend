package com.kdongd.norunnolife.service;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.domain.WorkoutType;
import com.kdongd.norunnolife.dto.WorkoutRequest;
import com.kdongd.norunnolife.dto.WorkoutResponse;
import com.kdongd.norunnolife.repository.MemoryWorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private MemoryWorkoutRepository workoutRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    @DisplayName("운동 기록 저장 후 응답값 반환")
    void createWorkout_success() {
        // given
        WorkoutRequest request = new WorkoutRequest(WorkoutType.RUNNING, 30, "테스트 메모", now);
        Workout saved = Workout.withId(1L, WorkoutType.RUNNING, 30, "테스트 메모", now);
        given(workoutRepository.save(any())).willReturn(saved);

        // when
        WorkoutResponse response = workoutService.createWorkout(request);

        // then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.type()).isEqualTo(WorkoutType.RUNNING);
        assertThat(response.durationMinutes()).isEqualTo(30);
        assertThat(response.memo()).isEqualTo("테스트 메모");
    }

    @Test
    @DisplayName("저장된 운동 기록 id 존재 검증")
    void createWorkout_idNotNull() {
        // given
        WorkoutRequest request = new WorkoutRequest(WorkoutType.BOXING, 60, null, now);
        Workout saved = Workout.withId(1L, WorkoutType.BOXING, 60, null, now);
        given(workoutRepository.save(any())).willReturn(saved);

        // when
        WorkoutResponse response = workoutService.createWorkout(request);

        // then
        assertThat(response.id()).isNotNull();
    }

    @Test
    @DisplayName("저장된 운동 기록이 없을 때 빈 리스트 반환")
    void getWorkouts_empty() {
        // given
        given(workoutRepository.findAll()).willReturn(List.of());

        // when
        List<WorkoutResponse> result = workoutService.getWorkouts();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("저장된 운동 기록 전체 반환")
    void getWorkouts_multiple() {
        // given
        List<Workout> workouts = List.of(
                Workout.withId(1L, WorkoutType.RUNNING, 30, "메모1", now),
                Workout.withId(2L, WorkoutType.BOXING, 60, "메모2", now)
        );
        given(workoutRepository.findAll()).willReturn(workouts);

        // when
        List<WorkoutResponse> result = workoutService.getWorkouts();

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(1).id()).isEqualTo(2L);
    }

    @Test
    @DisplayName("존재하는 id 조회 성공")
    void getWorkout_success() {
        // given
        Workout workout = Workout.withId(1L, WorkoutType.RUNNING, 30, "메모", now);
        given(workoutRepository.findById(1L)).willReturn(Optional.of(workout));

        // when
        WorkoutResponse response = workoutService.getWorkout(1L);

        // then
        assertThat(response.id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 id 조회 시 NoSuchElementException 발생")
    void getWorkout_notFound() {
        // given
        given(workoutRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> workoutService.getWorkout(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("운동 기록을 찾을 수 없습니다.");
    }
}
