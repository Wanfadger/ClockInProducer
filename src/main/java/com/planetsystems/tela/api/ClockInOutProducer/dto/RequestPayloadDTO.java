package com.planetsystems.tela.api.ClockInOutProducer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayloadDTO<T extends Object> implements Serializable {

    List<T> data;

    @NotBlank(message = "requestType is mandatory")
    @NotEmpty(message = "requestType is mandatory")
    String requestType;

    @NotBlank(message = "schoolTelaNumber is mandatory")
    @NotEmpty(message = "schoolTelaNumber is mandatory")
    String schoolTelaNumber;

    @NotBlank(message = "academicTerm is mandatory")
    @NotEmpty(message = "academicTerm is mandatory")
    String academicTerm;
}
