package com.planetsystems.tela.api.ClockInOutProducer.controller.mobile;

import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockInRequest;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import com.planetsystems.tela.api.ClockInOutProducer.service.ClockInOutProducerService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/telaClockIn")
@Slf4j
@RequiredArgsConstructor
public class ClockInOutProducerController {

	private final ClockInOutProducerService clockInOutProducerService;


	@PostMapping("/one")
	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockIn(@RequestBody @Valid ClockInRequest clockIn)  {
		return clockInOutProducerService.publishClockIns(clockIn);
	}

	@PostMapping("/list")
	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockIn(@RequestBody @Valid List<ClockInRequest> clockIns)  {
		return clockInOutProducerService.publishClockIns(clockIns);
	}

	@GetMapping("/synchronize/{telaSchoolNumber}")
	public ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(@PathVariable String telaSchoolNumber , @RequestParam Map<String , String> queryParam)  {
		return clockInOutProducerService.synchronizeSchoolData(telaSchoolNumber , queryParam);
	}



}
