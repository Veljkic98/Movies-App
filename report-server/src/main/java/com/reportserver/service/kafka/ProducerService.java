package com.reportserver.service.kafka;

import com.reportserver.constants.KafkaConstants;
import com.reportserver.model.Event;

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