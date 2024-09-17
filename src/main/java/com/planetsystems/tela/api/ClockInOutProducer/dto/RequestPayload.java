package com.planetsystems.tela.api.ClockInOutProducer.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class RequestPayload<T extends Object> implements Serializable {
    List<T> data;
    RequestType requestType;
    String schoolTelaNumber;
    String academicTerm;
}
