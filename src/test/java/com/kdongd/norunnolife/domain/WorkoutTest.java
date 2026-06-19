package com.kdongd.norunnolife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class WorkoutTest {

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    @DisplayName("create - 모든 필드값 정상 세팅 확인")
    void create_fieldsMatch() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);

        assertThat(workout.getType()).isEqualTo(WorkoutType.RUNNING);
        assertThat(workout.getDurationMinutes()).isEqualTo(30);
        assertThat(workout.getMemo()).isEqualTo("메모");
        assertThat(workout.getWorkoutDateTime()).isEqualTo(now);
    }

    @Test
    @DisplayName("update - 모든 필드값 변경 확인")
    void update_fieldsChanged() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);
        LocalDateTime newDateTime = now.plusDays(1);

        workout.update(WorkoutType.BOXING, 60, "새 메모", newDateTime);

        assertThat(workout.getType()).isEqualTo(WorkoutType.BOXING);
        assertThat(workout.getDurationMinutes()).isEqualTo(60);
        assertThat(workout.getMemo()).isEqualTo("새 메모");
        assertThat(workout.getWorkoutDateTime()).isEqualTo(newDateTime);
    }

    @Test
    @DisplayName("addDetail - details 리스트에 추가 확인")
    void addDetail_success() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);
        WorkoutDetail detail = WorkoutDetail.create(workout, 1, "1km", 300, "페이스");

        workout.addDetail(detail);

        assertThat(workout.getDetails()).hasSize(1);
        assertThat(workout.getDetails().get(0).getLabel()).isEqualTo("1km");
    }
}