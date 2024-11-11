package com.planetsystems.tela.api.ClockInOutProducer.controller.mobile;

import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockInRequestDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.ClockOutRequestDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.RequestPayloadDTO;
import com.planetsystems.tela.api.ClockInOutProducer.dto.SystemAppFeedBack;
import com.planetsystems.tela.api.ClockInOutProducer.service.ClockInOutProducerService;
import jakarta.validation.Valid;
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



	@PostMapping("/schoolData")
	public ResponseEntity<SystemAppFeedBack<Boolean>> mobileSchoolData(@RequestBody @Valid RequestPayloadDTO requestPayloadDTO)  {
		return clockInOutProducerService.mobileSchoolData(requestPayloadDTO);
	}

//	@PostMapping("/one")
//	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockIn(@RequestBody @Valid ClockInRequestDTO clockIn)  {
//		return clockInOutProducerService.publishClockIns(clockIn);
//	}

	@PostMapping("/list")
	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockIn(@RequestBody @Valid List<ClockInRequestDTO> clockIns)  {
		return clockInOutProducerService.publishClockIns(clockIns);
	}

	@PostMapping("/outs")
	public ResponseEntity<SystemAppFeedBack<Boolean>> staffClockOuts(@RequestBody @Valid List<ClockOutRequestDTO> clockOuts)  {
		System.out.println("staffClockOuts "+clockOuts);
		return clockInOutProducerService.publishClockOuts(clockOuts);
	}

	/*@GetMapping("/synchronize/{telaSchoolNumber}")
	public ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(@PathVariable String telaSchoolNumber , @RequestParam Map<String , String> queryParam)  {
		return clockInOutProducerService.synchronizeSchoolData(telaSchoolNumber , queryParam);
	}*/

	  @GetMapping("/synchronize/{telaSchoolNumber}")
   public ResponseEntity<SystemAppFeedBack<Boolean>> synchronizeSchoolData(@PathVariable String telaSchoolNumber , @RequestParam Map<String , String> queryParam)  {
//    return clockInOutProducerService.synchronizeSchoolData(telaSchoolNumber , queryParam);
      return clockInOutProducerService.synchronizeRestSchoolData(telaSchoolNumber , queryParam);
   }





}
