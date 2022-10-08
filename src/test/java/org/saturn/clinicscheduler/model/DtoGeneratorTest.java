package org.saturn.clinicscheduler.model;

import org.saturn.clinicscheduler.model.dto.request.DepartmentRequestDTO;
import org.saturn.clinicscheduler.model.dto.request.DoctorCreateDto;
import org.saturn.clinicscheduler.model.dto.request.PatientCreateDto;
import org.saturn.clinicscheduler.model.dto.request.ScheduleUnpartitionedDto;
import org.saturn.clinicscheduler.model.dto.response.AppointmentResponseDto;
import org.saturn.clinicscheduler.model.dto.response.DepartmentResponseDto;
import org.saturn.clinicscheduler.model.dto.response.DoctorInfoDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.model.dto.response.ScheduleResponseDto;
import org.saturn.clinicscheduler.model.dto.response.SpecialityDto;
import org.saturn.clinicscheduler.model.entity.constant.Gender;

import java.time.LocalDate;

import static org.saturn.clinicscheduler.model.EntityGeneratorTest.*;

public class DtoGeneratorTest {

    public static DepartmentRequestDTO departmentRequestDTO() {
        return DepartmentRequestDTO.builder()
                .city(department().getCity())
                .district(department().getDistrict())
                .address(department().getAddress())
                .phoneNumber(department().getPhoneNumber())
                .build();
    }

    public static DepartmentResponseDto departmentResponseDto() {
        return DepartmentResponseDto.builder()
                .city(department().getCity())
                .district(department().getDistrict())
                .address(department().getAddress())
                .phoneNumber(department().getPhoneNumber())
                .build();
    }

    public static SpecialityDto specialityDto() {
        return new SpecialityDto(speciality().getName());
    }

    public static DoctorCreateDto doctorCreateDto() {
        return DoctorCreateDto.builder()
                .name(doctor().getName())
                .birthdate(doctor().getBirthdate().toString())
                .gender(doctor().getGender().toString())
                .specialityId(speciality().getId())
                .worksFrom(doctor().getWorksFromYear().toString())
                .phoneNumber(doctor().getPhoneNumber())
                .password(doctor().getPassword())
                .build();
    }

    public static DoctorInfoDto doctorInfoDto() {
        return DoctorInfoDto.builder()
                .name(doctorCreateDto().getName())
                .birthdate(doctorCreateDto().getBirthdate())
                .gender(Gender.valueOf(doctorCreateDto().getGender()))
                .speciality(speciality().getName())
                .worksFrom(doctorCreateDto().getWorksFrom())
                .phoneNumber(doctorCreateDto().getPhoneNumber())
                .build();
    }

    public static PatientCreateDto patientCreateDto() {
        return PatientCreateDto.builder()
                .name(patient().getName())
                .birthdate(LocalDate.from(patient().getBirthdate().toInstant()))
                .gender(patient().getGender())
                .chiPolicy(patient().getChiPolicy())
                .passport(patient().getPassport())
                .password(patient().getPassword())
                .phoneNumber(patient().getPhoneNumber())
                .build();
    }

    public static PatientInfoDto patientInfoDto() {
        return PatientInfoDto.builder()
                .name(patientCreateDto().getName())
                .birthdate(patientCreateDto().getBirthdate())
                .gender(patientCreateDto().getGender())
                .chiPolicy(patientCreateDto().getChiPolicy())
                .passport(patientCreateDto().getPassport())
                .phoneNumber(patientCreateDto().getPhoneNumber())
                .build();
    }

    public static ScheduleUnpartitionedDto scheduleUnpartitionedDto() {
        return ScheduleUnpartitionedDto.builder()
                .doctorId(schedule().getDoctor().getId())
                .departmentId(schedule().getDepartment().getId())
                .date(schedule().getDate())
                .startTime(schedule().getStartTime())
                .endTime(schedule().getEndTime())
                .cabinet(schedule().getCabinet())
                .build();
    }

    public static ScheduleResponseDto scheduleResponseDto() {
        return ScheduleResponseDto.builder()
                .doctorName(schedule().getDoctor().getName())
                .departmentAddress(schedule().getDepartment().getCity() + ", " + schedule().getDepartment().getAddress())
                .date(schedule().getDate())
                .startTime(schedule().getStartTime())
                .endTime(schedule().getEndTime())
                .cabinet(schedule().getCabinet())
                .isAvailable(schedule().getIsAvailable())
                .build();
    }

    public static AppointmentResponseDto appointmentResponseDto() {
        return AppointmentResponseDto.builder()
                .doctorName(schedule().getDoctor().getName())
                .departmentAddress(scheduleResponseDto().getDepartmentAddress())
                .cabinet(schedule().getCabinet())
                .patientInfo(patientInfoDto())
                .date(schedule().getDate())
                .startTime(schedule().getStartTime())
                .build();
    }

}
