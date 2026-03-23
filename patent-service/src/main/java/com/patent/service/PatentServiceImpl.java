package com.patent.service;


import com.patent.dto.PatentRequestDTO;
import com.patent.dto.PatentResponseDTO;
import com.patent.entity.Patent;
import com.patent.exception.EmailAlreadyExistException;
import com.patent.exception.PatientNotFoundException;
import com.patent.grpc.BillingServiceGrpc;
import com.patent.kafka.kafkaProducer;
import com.patent.mapper.PatentMapper;
import com.patent.repository.PatentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class PatentServiceImpl implements PatentService{

    private final PatentRepository patentRepository;
    private final BillingServiceGrpc billingServiceGrpc;
    private final kafkaProducer kafkaProducer;

    public PatentServiceImpl(PatentRepository patentRepository, BillingServiceGrpc billingServiceGrpc, kafkaProducer kafkaProducer) {
        this.patentRepository = patentRepository;
        this.billingServiceGrpc = billingServiceGrpc;
        this.kafkaProducer = kafkaProducer;
    }


    @Override
    public List<PatentResponseDTO> getAllServices() {
        List<Patent> patents = patentRepository.findAll();
        return patents.stream()
                .map(PatentMapper::toDTO).toList();

    }

    @Override
    public PatentResponseDTO savePatent(PatentRequestDTO patentRequestDTO) {
        if(patentRepository.existsByEmail(patentRequestDTO.getEmail())){
            throw new EmailAlreadyExistException("User is already register");
        }
        Patent patent = patentRepository.save(PatentMapper.toEntity(patentRequestDTO));

        billingServiceGrpc.createBill(patent.getId(), patent.getName(), patent.getEmail());

        kafkaProducer.sendEvent(patent);

        return PatentMapper.toDTO(patent);
    }


    @Override
    public PatentResponseDTO update(String id, PatentRequestDTO patentRequestDTO) {
        Patent patent =  patentRepository.findById(id)
                          .orElseThrow(()->new PatientNotFoundException("Patient not found with ID"));

       if(patentRepository.existsByEmailAndIdNot(patentRequestDTO.getEmail(), id)){
           throw new EmailAlreadyExistException("User is already register");
       }
        patent.setEmail(patentRequestDTO.getEmail());
        patent.setName(patentRequestDTO.getName());
        patent.setAddress(patentRequestDTO.getAddress());
        patent.setDateOfBirth(LocalDate.parse(patentRequestDTO.getDateOfBirth()));

        Patent savePatent =  patentRepository.save(patent);
        return PatentMapper.toDTO(savePatent);
    }


    @Override
    public void deletePatient(String id) {
        patentRepository.findById(id)
                .orElseThrow(()->new PatientNotFoundException("Patient not found with ID"));
        patentRepository.deleteById(id);
    }


}
