package org.saturn.clinicscheduler.mapper;

import org.mapstruct.Mapper;
import org.saturn.clinicscheduler.model.dto.request.PatientCreateDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.model.dto.response.PatientRestrictedInfoDto;
import org.saturn.clinicscheduler.model.entity.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientInfoDto toInfoDto(Patient patient);

    PatientRestrictedInfoDto toRestrictedInfoDto(Patient patient);

    Patient fromCreateDto(PatientCreateDto patientCreateDto);

    Patient fromCreateDto(PatientCreateDto patientCreateDto, Long id);

}
