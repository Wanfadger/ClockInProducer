package com.planetsystems.tela.api.ClockInOutProducer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum RequestType implements Serializable {
    SCHOOL("SCHOOL"),CLASSES("CLASSES"),STAFFS("STAFFS"),CLOCKINS("CLOCKINS"),CLOCKOUTS("CLOCKOUTS"), SUBJECTS("SUBJECTS"), LEARNER_HEADCOUNTS("LEARNER_HEADCOUNTS"),
    LEARNER_ATTENDANCES("LEARNER_ATTENDANCES"), DISTRICTS("DISTRICTS"), STAFF_DAILY_TIME_ATTENDANCES("STAFF_DAILY_TIME_ATTENDANCES"),
    UPDATE_TIMETABLE_LESSONS("UPDATE_TIMETABLE_LESSONS"),STAFF_DAILY_TIMETABLES("STAFF_DAILY_TIMETABLES"), STAFF_DAILY_TASK_SUPERVISIONS("STAFF_DAILY_TASK_SUPERVISIONS"),
    SCHOOL_COORDINATES("SCHOOL_COORDINATES");

        String type;

        public static Optional<RequestType> fromString(String requestType){
        return Arrays.stream(RequestType.values()).filter(re -> requestType.equalsIgnoreCase(re.type)).findFirst();
        }


}
