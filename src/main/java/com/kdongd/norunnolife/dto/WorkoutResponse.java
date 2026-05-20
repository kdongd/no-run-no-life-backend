package com.kdongd.norunnolife.dto;

import com.kdongd.norunnolife.domain.WorkoutType;

import java.time.LocalDateTime;

public record WorkoutResponse(
   Long id,
   WorkoutType type,
   int durationMinutes,
   String memo,
   LocalDateTime workoutDateTime
) {}
