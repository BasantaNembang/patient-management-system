package com.patent.kafka;

import com.patent.entity.Patent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatentEvent;

@Slf4j
@Service
public class kafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public kafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patent patent){
        PatentEvent patentEvent = PatentEvent.newBuilder()
                .setEmail(patent.getEmail())
                .setEventType("PATIENT_CREATED")
                .setName(patent.getName())
                .setPatientId(patent.getId())
                .build();
        try{
            kafkaTemplate.send("addedPatient", patentEvent.toByteArray());
            log.info("message send via kafka successfully");
        } catch (Exception e) {
            log.error("error occur when sending data via kafka");
            throw new RuntimeException(e);
        }
    }

}

