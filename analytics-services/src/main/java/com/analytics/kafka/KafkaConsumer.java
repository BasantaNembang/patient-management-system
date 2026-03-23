package com.analytics.kafka;


import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatentEvent;

@Service
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);


    @KafkaListener(topics = "addedPatient", groupId = "analytics-services")
    public void consumeKafkaEvent(byte[] message){
        try {
            PatentEvent patentEvent = PatentEvent.parseFrom(message);
            log.info("message received successfully {} ", patentEvent);
        } catch (InvalidProtocolBufferException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }



}


