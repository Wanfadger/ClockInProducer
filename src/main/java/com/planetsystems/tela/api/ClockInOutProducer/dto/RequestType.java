package com.planetsystems.tela.api.ClockInOutProducer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum RequestType implements Serializable {
    SCHOOL("SCHOOL"),CLASSES("CLASSES"),STAFFS("STAFFS"),CLOCKINS("CLOCKINS"), CLOCKIN("CLOCKIN"), SUBJECTS("SUBJECTS"), LEARNER_HEADCOUNTS("LEARNER_HEADCOUNTS"), LEARNER_ATTENDANCES("LEARNER_ATTENDANCES"), DISTRICTS("DISTRICTS");

        String type;

        public static Optional<RequestType> fromString(String requestType){
        return Arrays.stream(RequestType.values()).filter(re -> requestType.equalsIgnoreCase(re.type)).findFirst();
        }


}
