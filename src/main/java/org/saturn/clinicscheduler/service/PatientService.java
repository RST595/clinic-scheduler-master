package org.saturn.clinicscheduler.service;

import org.saturn.clinicscheduler.model.dto.request.PatientCreateDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;

import java.util.List;

public interface PatientService {

    PatientInfoDto getPatient(Long id);

    PatientInfoDto createPatient(PatientCreateDto patientCreateDto);

    List<PatientInfoDto> getAllPatients();

    PatientInfoDto updatePatient(Long id, PatientCreateDto patientCreateDto);

    PatientInfoDto deletePatient(Long id);

}
