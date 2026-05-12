package com.kdongd.norunnolife.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Workout {

    private Long id;

    @NotNull(message = "운동 종류는 필수입니다.")
    private WorkoutType type;

    @Min(value = 1, message = "운동 시간은 1분 이상이어야 합니다.")
    private int duration;

    @NotBlank(message = "메모는 비어있을 수 없습니다.")
    private String memo;
}