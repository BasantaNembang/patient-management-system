package com.patent.mapper;

import com.patent.dto.PatentRequestDTO;
import com.patent.dto.PatentResponseDTO;
import com.patent.entity.Patent;

import java.time.LocalDate;

public class PatentMapper {

    public static PatentResponseDTO toDTO(Patent patent) {
        PatentResponseDTO dto = new PatentResponseDTO();
        dto.setId(patent.getId());
        dto.setAddress(patent.getId());
        dto.setEmail(patent.getEmail());
        dto.setName(patent.getName());
        dto.setAddress(patent.getAddress());
        dto.setDateOfBirth(patent.getDateOfBirth().toString());
        return dto;
    }

    public static Patent toEntity(PatentRequestDTO dto) {
        Patent patent = new Patent();
        patent.setName(dto.getName());
        patent.setEmail(dto.getEmail());
        patent.setAddress(dto.getAddress());
        patent.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        patent.setRegisterDate(LocalDate.parse(dto.getRegisterDate()));
        return patent;
    }

}
