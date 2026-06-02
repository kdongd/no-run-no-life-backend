package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.domain.WorkoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryWorkoutRepositoryTest {

    private MemoryWorkoutRepository repository;
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new MemoryWorkoutRepository();
    }

    @Test
    @DisplayName("저장 후 id 자동 증가 검증")
    void save_idAutoIncrement() {
        // given
        Workout workout1 = Workout.create(WorkoutType.RUNNING, 30, "메모1", now);
        Workout workout2 = Workout.create(WorkoutType.BOXING, 60, "메모2", now);

        // when
        Workout saved1 = repository.save(workout1);
        Workout saved2 = repository.save(workout2);

        // then
        assertThat(saved1.getId()).isEqualTo(1L);
        assertThat(saved2.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("저장 후 반환된 데이터 필드값 검증")
    void save_fieldValues() {
        // given
        Workout workout = Workout.create(WorkoutType.RUNNING, 30, "테스트 메모", now);

        // when
        Workout saved = repository.save(workout);

        // then
        assertThat(saved.getType()).isEqualTo(WorkoutType.RUNNING);
        assertThat(saved.getDurationMinutes()).isEqualTo(30);
        assertThat(saved.getMemo()).isEqualTo("테스트 메모");
        assertThat(saved.getWorkoutDateTime()).isEqualTo(now);
    }

    @Test
    @DisplayName("빈 저장소에서 빈 리스트 반환")
    void findAll_empty() {
        // when
        List<Workout> result = repository.findAll();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("여러 건 저장 후 전체 반환")
    void findAll_multiple() {
        // given
        repository.save(Workout.create(WorkoutType.RUNNING, 30, "메모1", now));
        repository.save(Workout.create(WorkoutType.BOXING, 60, "메모2", now));

        // when
        List<Workout> result = repository.findAll();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("존재하는 id 조회 성공")
    void findById_success() {
        // given
        Workout saved = repository.save(Workout.create(WorkoutType.RUNNING, 30, "메모", now));

        // when
        Optional<Workout> result = repository.findById(saved.getId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("존재하지 않는 id 조회 시 Optional.empty() 반환")
    void findById_notFound() {
        // when
        Optional<Workout> result = repository.findById(999L);

        // then
        assertThat(result).isEmpty();
    }
}