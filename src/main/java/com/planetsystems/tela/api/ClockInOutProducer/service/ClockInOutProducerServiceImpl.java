package com.planetsystems.tela.api.ClockInOutProducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planetsystems.tela.api.ClockInOutProducer.dto.*;
import jakarta.jms.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

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

    @Value("${queue.clockouts}")
    private String clockOutsQueue;



    @Value("${queue.learnerHeadCounts}")
    private  String learnerHeadCountsQueue;

    @Value("${queue.classAttendances}")
    private  String classAttendancesQueue;

    @Value("${queue.classes}")
    private  String classesQueue;

    @Value("${queue.staffs}")
    private  String staffsQueue;

    @Value("${queue.staffDailyTimeAttendances}")
    private String staffDailyTimeAttendancesQueue;

    @Value("${queue.updateTimetableLessons}")
    private String updateTimetableLessonsQueue;

    @Value("${queue.staffDailyTimetables}")
    private String staffDailyTimetablesQueue;


    @Value("${queue.staffDailyTaskSupervisions}")
    private String staffDailyTaskSupervisionsQueue;

    @Value("${queue.schoolCoordinate}")
    private String schoolCoordinateQueue;
    @Value("${queue.synchronizeMobileData}")
    private  String synchronizeMobileData;



    private final ObjectMapper objectMapper;

    final RestClient restClient;





    @Override
    public ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(String telaSchoolNumber ,  Map<String , String> queryParam) {
        try{
            queryParam.put("telaSchoolNumber" , telaSchoolNumber);
            jmsTemplate.setPubSubDomain(false);
            jmsTemplate.convertAndSend(synchronizeMobileData , queryParam);
            log.info("Successfully published synchronizeSchoolData {} " , queryParam);
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }

    @Override
    public ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeRestSchoolData(String telaSchoolNumber, Map<String, String> queryParam) {
        try {

            queryParam.put("telaSchoolNumber" , telaSchoolNumber);
            SynchronizeRestSchoolDataDTO synchronizeRestSchoolDataDTO = new SynchronizeRestSchoolDataDTO(telaSchoolNumber, queryParam.get("date"));
            Boolean body = restClient.post()
                    .uri( "/SynchroniseTelaData")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(synchronizeRestSchoolDataDTO)
                    .retrieve()
                    .body(Boolean.class);
            log.info("request body {} " , synchronizeRestSchoolDataDTO);
            log.info("synchronizeRestSchoolData Consumer RESPONSE {} ", body);
            if (body.booleanValue()) {
                return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message("success").build());
    }

    @Override
    public ResponseEntity<SystemAppFeedBack<Boolean>> mobileSchoolData(RequestPayloadDTO requestPayloadDTO) {
        Optional<RequestType> requestTypeOptional = RequestType.fromString(requestPayloadDTO.getRequestType());

        if (requestTypeOptional.isPresent()) {
            RequestType requestType = requestTypeOptional.get();
            switch (requestType){
                case LEARNER_HEADCOUNTS -> {
                    log.info("PUBLISHING LEARNER_HEADCOUNTS {} ", requestPayloadDTO.getRequestType());
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(learnerHeadCountsQueue, publishPayloadDTO);
                }
                    case LEARNER_ATTENDANCES -> {
                        log.info("PUBLISHING LEARNER_ATTENDANCES {} " , requestPayloadDTO.getRequestType());
                        SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                                .academicTerm(requestPayloadDTO.getAcademicTerm())
                                .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                                .data(requestPayloadDTO.getData())
                                .build();
                        publishSchoolData(classAttendancesQueue ,publishPayloadDTO);
                }

                case CLASSES -> {
                    log.info("PUBLISHING CLASSES {} " , requestPayloadDTO.getRequestType());
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(classesQueue ,publishPayloadDTO);
                }

                case STAFFS -> {
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(staffsQueue ,publishPayloadDTO);
                    log.info("PUBLISHING STAFFS {} " , requestPayloadDTO.getRequestType());
                }

                case STAFF_DAILY_TIME_ATTENDANCES -> {
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(staffDailyTimeAttendancesQueue ,publishPayloadDTO);
                    log.info("PUBLISHING STAFF_DAILY_TIME_ATTENDANCES {} " , requestPayloadDTO.getRequestType());
                }

                case UPDATE_TIMETABLE_LESSONS -> {
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(updateTimetableLessonsQueue ,publishPayloadDTO);
                    log.info("PUBLISHING UPDATE_TIMETABLE_LESSONS {} " , requestPayloadDTO.getRequestType());
                }

                case STAFF_DAILY_TIMETABLES -> {
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();

                    publishSchoolData(staffDailyTimetablesQueue ,publishPayloadDTO);
                    log.info("PUBLISHING STAFF_DAILY_TIMETABLES {} " , requestPayloadDTO.getRequestType());
                }

                case STAFF_DAILY_TASK_SUPERVISIONS -> {
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(staffDailyTaskSupervisionsQueue ,publishPayloadDTO);
                    log.info("PUBLISHING STAFF_DAILY_TASK_SUPERVISIONS {} " , requestPayloadDTO.getRequestType());
                }

                case SCHOOL_COORDINATES -> {
                    SchoolDataPublishPayloadDTO<Object> publishPayloadDTO = SchoolDataPublishPayloadDTO.builder()
                            .academicTerm(requestPayloadDTO.getAcademicTerm())
                            .schoolTelaNumber(requestPayloadDTO.getSchoolTelaNumber())
                            .data(requestPayloadDTO.getData())
                            .build();
                    publishSchoolData(schoolCoordinateQueue ,publishPayloadDTO);
                    log.info("PUBLISHING SCHOOL_COORDINATES {} " , requestPayloadDTO.getRequestType());
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


//    @Override
//    @Transactional
//    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(ClockInRequestDTO clockIn) {
//        try{
//            jmsTemplate.setPubSubDomain(false);
//            jmsTemplate.convertAndSend(clockInQueue , objectMapper.writeValueAsString(clockIn));
//            log.info("Successfully published {} " , clockIn);
//            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
//        }
//    }
//
    @Override
    @Transactional
    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockIns(List<ClockInRequestDTO> clockIns) {
        try{
            publish(clockInsQueue , objectMapper.writeValueAsString(clockIns));
            log.info("publishClockIns DATA {}" , clockIns);
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }


    @Override
    public ResponseEntity<SystemAppFeedBack<Boolean>> publishClockOuts(List<ClockOutRequestDTO> clockOuts) {
        log.info("CLOCKOURS DATA {}" , clockOuts);
        try{
            publish(clockOutsQueue , objectMapper.writeValueAsString(clockOuts));
            log.info("publishClockOuts DATA {}" , clockOuts);
            return ResponseEntity.ok(SystemAppFeedBack.<Boolean>builder().data(true).status(true).message("success").build());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SystemAppFeedBack.<Boolean>builder().data(false).status(false).message(e.getMessage()).build());
        }
    }


    public void publish(@NonNull String queueName ,@NonNull String dataStr){
        jmsTemplate.setPubSubDomain(false);
        jmsTemplate.convertAndSend(queueName , dataStr);
    }




}
