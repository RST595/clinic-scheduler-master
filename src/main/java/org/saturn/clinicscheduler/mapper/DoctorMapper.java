package org.saturn.clinicscheduler.mapper;

import org.mapstruct.Mapper;
import org.saturn.clinicscheduler.model.dto.request.DoctorCreateDto;
import org.saturn.clinicscheduler.model.dto.response.DoctorInfoDto;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.constant.Gender;
import org.saturn.clinicscheduler.model.entity.Speciality;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    default DoctorInfoDto mapToInfoDto(Doctor doctor) {
        return DoctorInfoDto.builder()
                .name(doctor.getName())
                .birthdate(doctor.getBirthdate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .gender(doctor.getGender())
                .speciality(doctor.getSpeciality().getName())
                .worksFrom(doctor.getWorksFromYear().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .phoneNumber(doctor.getPhoneNumber())
                .build();
    }

    default Doctor mapToDoctor(DoctorCreateDto doctorCreateDto, Speciality speciality) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(doctorCreateDto.getBirthdate(), formatter);
        LocalDate worksFrom = LocalDate.parse(doctorCreateDto.getWorksFrom(), formatter);

        Doctor doctor = new Doctor();
        doctor.setName(doctorCreateDto.getName());
        doctor.setBirthdate(birthdate);
        doctor.setGender(Gender.valueOf(doctorCreateDto.getGender()));
        doctor.setSpeciality(speciality);
        doctor.setWorksFromYear(worksFrom);
        doctor.setPhoneNumber(doctorCreateDto.getPhoneNumber());
        doctor.setPassword(doctorCreateDto.getPassword());

        return doctor;
    }

}
