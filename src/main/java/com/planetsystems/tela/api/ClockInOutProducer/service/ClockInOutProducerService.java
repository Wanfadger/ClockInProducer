package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.planetsystems.tela.api.ClockInOutProducer.dto.SyncClockIn;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClockInOutProducerService {
    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(SyncClockIn clockIn);
    ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(List<SyncClockIn> clockIns);
    ;
}
