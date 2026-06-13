package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.domain.WorkoutType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(JpaWorkoutRepository.class)
class JpaWorkoutRepositoryTest {

    @Autowired
    private JpaWorkoutRepository workoutRepository;

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    @DisplayName("save - 저장 후 id 생성 확인")
    void save_generatesId() {
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "메모", now);

        Workout saved = workoutRepository.save(workout);

        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("save - 저장된 필드값 일치 확인")
    void save_fieldsMatch() {
        Workout workout = Workout.create(WorkoutType.BOXING, 60, "복싱 메모", now);

        Workout saved = workoutRepository.save(workout);

        assertThat(saved.getType()).isEqualTo(WorkoutType.BOXING);
        assertThat(saved.getDurationMinutes()).isEqualTo(60);
        assertThat(saved.getMemo()).isEqualTo("복싱 메모");
    }

    @Test
    @DisplayName("findById - 저장된 엔티티 조회 성공")
    void findById_success() {
        Workout saved = workoutRepository.save(Workout.create(WorkoutType.RUNNING, 30, "메모", now));

        Optional<Workout> result = workoutRepository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("findById - 존재하지 않는 id 조회 시 empty 반환")
    void findById_notFound() {
        Optional<Workout> result = workoutRepository.findById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("findAll - 저장된 전체 목록 반환")
    void findAll_returnsAll() {
        workoutRepository.save(Workout.create(WorkoutType.RUNNING, 30, "메모1", now));
        workoutRepository.save(Workout.create(WorkoutType.BOXING, 60, "메모2", now));

        List<Workout> result = workoutRepository.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("delete - 삭제 후 findById empty 반환")
    void delete_thenNotFound() {
        Workout saved = workoutRepository.save(Workout.create(WorkoutType.RUNNING, 30, "메모", now));

        workoutRepository.delete(saved);

        assertThat(workoutRepository.findById(saved.getId())).isEmpty();
    }
}