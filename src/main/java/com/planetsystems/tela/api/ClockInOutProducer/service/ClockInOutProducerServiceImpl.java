package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SyncClockIn;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClockInOutProducerServiceImpl implements ClockInOutProducerService{
    private final JmsTemplate jmsTemplate;

    @Value("${queue.clockins}")
    private String CLOCKInsQueue;

    @Value("${queue.clockin}")
    private  String CLOCKInQueue;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(SyncClockIn clockIn) {
        try{
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(CLOCKInQueue , objectMapper.writeValueAsString(clockIn));
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(List<SyncClockIn> clockIns) {
        try{
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(CLOCKInsQueue , objectMapper.writeValueAsString(clockIns));
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }

}
