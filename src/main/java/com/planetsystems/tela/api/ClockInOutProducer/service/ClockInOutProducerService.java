package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockInRequestDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockOutRequestDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.RequestPayloadDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ClockInOutProducerService {
//    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(ClockInRequestDTO clockIn);
    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(List<ClockInRequestDTO> clockIns);

    ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(String telaSchoolNumber ,  Map<String , String> queryParam);

    ResponseEntity<SystemAppFeedBack<Boolean>> mobileSchoolData(RequestPayloadDTO requestPayloadDTO);

    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockOuts(List<ClockOutRequestDTO> clockOuts);

    ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeRestSchoolData(String telaSchoolNumber ,  Map<String , String> queryParam);
}
