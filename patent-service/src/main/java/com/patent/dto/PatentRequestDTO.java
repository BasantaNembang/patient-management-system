package com.patent.dto;

import com.patent.dto.Validators.InputValidatorsGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatentRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 20, message = "Can`t exceed more then 20 character")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Date of Birth is required")
    private String dateOfBirth;

    @NotBlank(groups = InputValidatorsGroup.class,
            message = "Register Date is required")
    private String registerDate;



}
