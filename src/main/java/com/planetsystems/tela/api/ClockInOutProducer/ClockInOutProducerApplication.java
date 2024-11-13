package com.planetsystems.tela.api.ClockInOutProducer;

import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockInRequestDTO;
import com.planetsystems.tela.api.ClockInOutProducer.service.ClockInOutProducerService;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class ClockInOutProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ClockInOutProducerApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

//
		ClockInRequestDTO request = ClockInRequestDTO.builder()
				.academicTermId("8a8089b191dfdd700191dfe76bc80018")
				.clockInDateTime("12/09/2024 16:28:42")
				.clockInType("Pin")
				.displacement(1)
				.telaSchoolNumber("8008221080822")
				.staffId("8a8089108e413138018e42b383ed7972")
				.latitude("0.3277858")
				.longitude("32.6128736")
				.build();


		ClockInRequestDTO request2 = ClockInRequestDTO.builder()
				.academicTermId("8a8089b191dfdd700191dfe76bc80018")
				.clockInDateTime("12/09/2024 16:28:42")
				.clockInType("Pin")
				.displacement(1)
				.telaSchoolNumber("8008221080822")
				.staffId("8a8089108e413138018e42b383ed7972")
				.latitude("0.3277858")
				.longitude("32.6128736")
				.build();

//		List.of(1,2,2,3,4,5,6).forEach(integer -> {
//			clockInOutProducerService.publishClockIns(request);
//			clockInOutProducerService.publishClockIns(request2);
//			log.info("PRODUCER "+integer);
//		});

	}

}
