package org.app.patientservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.app.patientservice.dto.PatientRequestDTO;
import org.app.patientservice.dto.PatientResponseDTO;
import org.app.patientservice.exception.EmailAlreadyExistsException;
import org.app.patientservice.exception.PatientNotFoundException;
import org.app.patientservice.grpc.BillingServiceGrpcClient;
import org.app.patientservice.mapper.PatientMapper;
import org.app.patientservice.model.Patient;
import org.app.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.app.patientservice.kafka.KafkaProducer;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
       return patients.stream().map(PatientMapper::toPatientResponseDto).toList();
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("The email address already exists !");
        }
        Patient savedPatient = patientRepository.save(PatientMapper.toPatient(patientRequestDTO));
        billingServiceGrpcClient.createBillingAccount(savedPatient.getId().toString()
                ,savedPatient.getName(),savedPatient.getEmail());
        kafkaProducer.senEvent(savedPatient);
        return PatientMapper.toPatientResponseDto(savedPatient);
    }

    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException("The patient with the id " + id + " already exists !"));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("The email "+patientRequestDTO.getEmail()+" address already exists !");
        }
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setAddress(patientRequestDTO.getAddress());
        patientRepository.save(patient);
        return PatientMapper.toPatientResponseDto(patient);

    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }


}
