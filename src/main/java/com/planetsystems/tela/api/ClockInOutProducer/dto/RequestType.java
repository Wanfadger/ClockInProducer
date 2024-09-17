package com.planetsystems.tela.api.ClockInOutProducer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public enum RequestType implements Serializable {
    SCHOOL("SCHOOL"),CLASSES("CLASSES"),STAFFS("STAFFS"),CLOCKINS("CLOCKINS"), CLOCKIN("CLOCKIN"), SUBJECTS("SUBJECTS"), LEARNER_HEADCOUNTS("LEARNER_HEADCOUNTS"), LEARNER_ATTENDANCES("LEARNER_ATTENDANCES"), DISTRICTS("DISTRICTS");

        String type;
}
