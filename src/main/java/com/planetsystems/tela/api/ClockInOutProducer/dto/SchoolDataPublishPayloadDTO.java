package com.planetsystems.tela.api.ClockInOutProducer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchoolDataPublishPayloadDTO<T> {
    T data;

    @NotBlank(message = "schoolTelaNumber is mandatory")
    @NotEmpty(message = "schoolTelaNumber is mandatory")
    String schoolTelaNumber;

    @NotBlank(message = "academicTerm is mandatory")
    @NotEmpty(message = "academicTerm is mandatory")
    String academicTerm;
}