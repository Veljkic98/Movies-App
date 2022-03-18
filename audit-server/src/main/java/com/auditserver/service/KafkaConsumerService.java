package com.auditserver.service;

import com.auditserver.constants.KafkaConstants;
import com.auditserver.model.Event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {

    @Autowired
    private EventService eventService;

    @KafkaListener(
        topics = {
            KafkaConstants.TOPIC_EVENT_NAME_REPORT,
            KafkaConstants.TOPIC_EVENT_NAME_AUTH
        },
        groupId = "group_json",  // grupe klijenata
        containerFactory = "eventKafkaListenerContainerFactory"
    )
    public void processMessageEvent(Event event) {
        
        // just print the event, for easier tracking
        System.out.println("Message received: " + event);

        eventService.save(event);
    }
    
}
