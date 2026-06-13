package com.kdongd.norunnolife.repository;

import com.kdongd.norunnolife.domain.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutJpaRepository extends JpaRepository<Workout, Long> {
}