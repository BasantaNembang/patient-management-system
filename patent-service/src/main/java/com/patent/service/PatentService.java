package com.patent.service;

import com.patent.dto.PatentRequestDTO;
import com.patent.dto.PatentResponseDTO;


import java.util.List;

public interface PatentService {

    List<PatentResponseDTO> getAllServices();

    PatentResponseDTO savePatent(PatentRequestDTO patentRequestDTO);

    PatentResponseDTO update(String id, PatentRequestDTO patentRequestDTO);

    void deletePatient(String id);
}
