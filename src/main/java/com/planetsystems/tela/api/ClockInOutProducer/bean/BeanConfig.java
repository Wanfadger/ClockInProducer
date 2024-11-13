package com.planetsystems.tela.api.ClockInOutProducer.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class BeanConfig {

	@Value("${consumerBaseUrl}")
	String consumerBaseUrl;

	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}

	@Bean
	public RestClient restClient(){
		return RestClient.create(consumerBaseUrl);
	}

}
