package org.app.patientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.app.patientservice.dto.PatientRequestDTO;
import org.app.patientservice.dto.PatientResponseDTO;
import org.app.patientservice.dto.validator.CreatePatientValidationGroup;
import org.app.patientservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patients",description = "API for mapping patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    @Operation(summary = "Create a patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({
            Default.class, CreatePatientValidationGroup.class
    }) @RequestBody PatientRequestDTO patientRequestDTO) {
        return new ResponseEntity<>(patientService.createPatient(patientRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,
                                            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        return new ResponseEntity<>(patientService.updatePatient(id,patientRequestDTO), HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
