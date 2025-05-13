package org.app.patientservice.mapper;

import org.app.patientservice.dto.PatientRequestDTO;
import org.app.patientservice.dto.PatientResponseDTO;
import org.app.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toPatientResponseDto(Patient patient) {
       return new PatientResponseDTO(
                patient.getId().toString(),
                patient.getName(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getDateOfBirth().toString()
        );
    }

    public static Patient toPatient(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        return patient;
    }
}
