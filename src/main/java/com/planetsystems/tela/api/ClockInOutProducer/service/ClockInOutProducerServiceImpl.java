package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetsystems.tela.api.ClockInOutProducer.dto.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClockInOutProducerServiceImpl implements ClockInOutProducerService{
    private final JmsTemplate jmsTemplate;

    @Value("${queue.clockins}")
    private String clockInsQueue;

    @Value("${queue.clockin}")
    private  String clockInQueue;

    @Value("${queue.learnerHeadCounts}")
    private  String learnerHeadCountsQueue;

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
    public ResponseEntity<SystemAppFeedBack<Boolean>> mobileSchoolData(RequestPayloadDTO requestPayloadDTO) {
        Optional<RequestType> requestTypeOptional = RequestType.fromString(requestPayloadDTO.getRequestType());

        if (requestTypeOptional.isPresent()) {
            RequestType requestType = requestTypeOptional.get();
            switch (requestType){
                case LEARNER_HEADCOUNTS -> {
                    log.info("PUBLISHING {} " , requestPayloadDTO.getRequestType());
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(learnerHeadCountsQueue ,publishPayloadDTO);
                }
            }
        }


        log.info("mobileSchoolData  {} "  , requestPayloadDTO);
        return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("successfully posted school data").build());
    }



    @Async
    public void publishSchoolData(@NotEmpty(message = "name is required") @NotBlank(message = "name is required") String queueName , SchoolDataPublishPayloadDTO schoolDataPublishPayloadDTO){
        try{
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(queueName , objectMapper.writeValueAsString(schoolDataPublishPayloadDTO));
        }catch (Exception e){
            e.printStackTrace();
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
