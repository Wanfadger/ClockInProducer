package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockInRequest;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClockInOutProducerServiceImpl implements ClockInOutProducerService{
    private final JmsTemplate jmsTemplate;

    @Value("${queue.clockins}")
    private String clockInsQueue;

    @Value("${queue.clockin}")
    private  String clockInQueue;

    @Value("${queue.synchronizeMobileData}")
    private  String synchronizeMobileData;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(String telaSchoolNumber ,  Map<String , String> queryParam) {
        try{
            queryParam.put("telaSchoolNumber" , telaSchoolNumber);
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(synchronizeMobileData , queryParam);
            log.info("Successfully published telaSchoolNumber {} " , queryParam);
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }


    @Override
    @Transactional
    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(ClockInRequest clockIn) {
        try{
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(clockInQueue , objectMapper.writeValueAsString(clockIn));
            log.info("Successfully published {} " , clockIn);
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(List<ClockInRequest> clockIns) {
        try{
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(clockInsQueue , objectMapper.writeValueAsString(clockIns));
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }



}
