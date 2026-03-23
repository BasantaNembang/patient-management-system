package com.patent.cotroller;


import com.patent.dto.PatentRequestDTO;
import com.patent.dto.PatentResponseDTO;
import com.patent.dto.Validators.InputValidatorsGroup;
import com.patent.service.PatentServiceImpl;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/patent")
public class PatentController {

    private final PatentServiceImpl patentService;

    public PatentController(PatentServiceImpl patentService) {
        this.patentService = patentService;
    }

    @PostMapping("/save")
    public ResponseEntity<PatentResponseDTO> savePatent(
                          @Validated({Default.class, InputValidatorsGroup.class})
                          @RequestBody PatentRequestDTO patentRequestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(patentService.savePatent(patentRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatentResponseDTO> updatePatent(
                     @PathVariable("id") String id,
                     @Validated({Default.class}) @RequestBody PatentRequestDTO patentRequestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(patentService.update(id, patentRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<List<PatentResponseDTO>> getAllPatents(){
        return ResponseEntity.status(HttpStatus.OK).body(patentService.getAllServices());
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") String id){
         patentService.deletePatient(id);
         return ResponseEntity.noContent().build();
    }


}



