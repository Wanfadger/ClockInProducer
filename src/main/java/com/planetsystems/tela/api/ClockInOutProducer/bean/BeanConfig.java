package com.planetsystems.tela.api.ClockInOutProducer.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

@Configuration
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
