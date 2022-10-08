package org.saturn.clinicscheduler.controller;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.model.dto.request.PatientCreateDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PatientInfoDto> createPatient(@Valid @RequestBody PatientCreateDto patientCreateDto) {
        return ResponseEntity.ok(patientService.createPatient(patientCreateDto));
    }

    @GetMapping
    public ResponseEntity<List<PatientInfoDto>> getAllPatient() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientInfoDto> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientCreateDto patientCreateDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PatientInfoDto> deletePatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }

}
