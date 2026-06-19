package com.kdongd.norunnolife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class WorkoutDetailTest {

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    @DisplayName("create - 모든 필드값 정상 세팅 확인")
    void create_fieldsMatch() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);
        WorkoutDetail detail = WorkoutDetail.create(workout, 1, "1km", 300, "페이스");

        assertThat(detail.getWorkout()).isEqualTo(workout);
        assertThat(detail.getSequence()).isEqualTo(1);
        assertThat(detail.getLabel()).isEqualTo("1km");
        assertThat(detail.getDurationSeconds()).isEqualTo(300);
        assertThat(detail.getNote()).isEqualTo("페이스");
    }
}