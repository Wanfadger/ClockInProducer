package com.planetsystems.tela.api.ClockInOutProducer.controller.mobile;

import com.planetsystems.tela.api.ClockInOutProducer.dto.SyncClockIn;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import com.planetsystems.tela.api.ClockInOutProducer.service.ClockInOutProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/telaClockIn")
@Slf4j
@RequiredArgsConstructor
public class ClockInOutProducerController {

	private final ClockInOutProducerService clockInOutProducerService;


	@PostMapping("/one")
	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockIn(@RequestBody @Valid SyncClockIn clockIn)  {
		return clockInOutProducerService.publishClockIns(clockIn);
	}

	@PostMapping("/list")
	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockIn(@RequestBody @Valid List<SyncClockIn> clockIns)  {
		return clockInOutProducerService.publishClockIns(clockIns);
	}

}
