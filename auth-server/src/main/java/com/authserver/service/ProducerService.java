package com.authserver.service;

import com.authserver.constants.KafkaConstants;
import com.authserver.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

	@Autowired
	private KafkaTemplate<String, Event> kafkaTemplate;
	
	public void send(Event event) {
	    
	    kafkaTemplate.send(KafkaConstants.TOPIC_EVENT_NAME_REPORT, event);
	}
    
}
