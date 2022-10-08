package org.saturn.clinicscheduler.service.impl;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.exception.BusyPassportException;
import org.saturn.clinicscheduler.exception.BusyPhoneNumberException;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.mapper.PatientMapper;
import org.saturn.clinicscheduler.model.dto.request.PatientCreateDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.model.entity.Patient;
import org.saturn.clinicscheduler.repository.PatientRepository;
import org.saturn.clinicscheduler.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientMapper patientMapper;
    private final PatientRepository patientRepository;

    @Override
    public PatientInfoDto getPatient(Long id) {
        Patient patient = findPatientById(id);

        return patientMapper.toInfoDto(patient);
    }

    @Override
    @Transactional
    public PatientInfoDto createPatient(PatientCreateDto patientCreateDto) {
        throwIfPhoneBusy(patientCreateDto.getPhoneNumber());
        throwIfPassportBusy(patientCreateDto.getPassport());

        return patientMapper.toInfoDto(patientRepository.save(patientMapper.fromCreateDto(patientCreateDto)));
    }

    @Override
    public List<PatientInfoDto> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patientMapper::toInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PatientInfoDto updatePatient(Long id, PatientCreateDto patientCreateDto) {
        Patient patient = findPatientById(id);
        String newPassport = patientCreateDto.getPassport();
        String newPhone = patientCreateDto.getPhoneNumber();
        if (!patient.getPassport().equals(newPassport)) {
            throwIfPassportBusy(newPassport);
        }
        if (!patient.getPhoneNumber().equals(newPhone)) {
            throwIfPhoneBusy(newPhone);
        }

        return patientMapper.toInfoDto(patientRepository.save(patientMapper.fromCreateDto(patientCreateDto, id)));
    }

    @Override
    public PatientInfoDto deletePatient(Long id) {
        PatientInfoDto patient = getPatient(id);
        patientRepository.deleteById(id);

        return patient;
    }

    private void throwIfPhoneBusy(String phone) {
        patientRepository.findByPhoneNumber(phone).ifPresent((s) -> {
            throw new BusyPhoneNumberException();
        });
    }

    private void throwIfPassportBusy(String passport) {
        patientRepository.findByPassport(passport).ifPresent((s) -> {
            throw new BusyPassportException();
        });
    }

    private Patient findPatientById(Long id) {
        return patientRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new ObjectNotFoundException("Patient");
                });
    }

}
