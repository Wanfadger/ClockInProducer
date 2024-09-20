package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockInRequest;
import com.planetsystems.tela.api.ClockInOutProducer.dto.RequestPayloadDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ClockInOutProducerService {
    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(ClockInRequest clockIn);
    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(List<ClockInRequest> clockIns);

    ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(String telaSchoolNumber ,  Map<String , String> queryParam);

    ResponseEntity<SystemAppFeedBack<Boolean>> mobileSchoolData(RequestPayloadDTO requestPayloadDTO);
}
