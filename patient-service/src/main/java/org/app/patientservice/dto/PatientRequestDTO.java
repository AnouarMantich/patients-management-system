package org.app.patientservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.patientservice.dto.validator.CreatePatientValidationGroup;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class PatientRequestDTO {
    @NotBlank(message = "The name is required !")
    @Size(max = 100 ,message = "The name can not exceed 100 characters")
    private String name;
    @NotBlank(message = "The email is required !")
    @Email(message = "The email in not valid !")
    private String email;
    @NotBlank(message = "The address is required !")
    private String address;
    @NotBlank(message = "The date of birth is required !")
    private String dateOfBirth;
    @NotBlank(groups = CreatePatientValidationGroup.class,message = "The registration date is required !")
    private String registeredDate;

}
