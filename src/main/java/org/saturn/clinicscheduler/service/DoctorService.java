package org.saturn.clinicscheduler.service;

import org.saturn.clinicscheduler.model.dto.request.DoctorCreateDto;
import org.saturn.clinicscheduler.model.dto.response.DoctorInfoDto;
import org.saturn.clinicscheduler.model.dto.response.SpecialityDto;
import org.saturn.clinicscheduler.model.entity.Speciality;

import java.util.List;

public interface DoctorService {

    List<Speciality> getAllSpecialities();

    Speciality changeSpeciality(Long id, String title);

    List<DoctorInfoDto> getDoctorsBySpecialityId(Long id);

    List<DoctorInfoDto> getAllDoctors();

    DoctorInfoDto createDoctor(DoctorCreateDto doctorDto);

    DoctorInfoDto deleteDoctor(Long id);

    Speciality deleteSpeciality(Long id);

    SpecialityDto createSpeciality(SpecialityDto specialityDto);

    DoctorInfoDto updateDoctor(Long id, DoctorCreateDto doctorDto);

}
