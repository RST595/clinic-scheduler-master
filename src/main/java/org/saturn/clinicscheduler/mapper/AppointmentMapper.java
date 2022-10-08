package org.saturn.clinicscheduler.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.saturn.clinicscheduler.model.dto.response.AppointmentResponseDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.model.entity.Appointment;
import org.saturn.clinicscheduler.model.entity.Schedule;

@Mapper(componentModel = "spring", uses = AppointmentMapper.class)
public interface AppointmentMapper {

    @Mapping(target = "doctorName", expression = "java(appointment.getDoctor().getName())")
    @Mapping(target = "departmentAddress", expression = "java(schedule.getDepartment().getCity() + \", \" "
            + "+ schedule.getDepartment().getAddress())")
    @Mapping(target = "cabinet", expression = "java(schedule.getCabinet())")
    @Mapping(target = "patientInfo", expression = "java(patientInfoDto)")
    @Mapping(target = "date", expression = "java(appointment.getDate())")
    @Mapping(target = "startTime", expression = "java(appointment.getStartTime())")
    AppointmentResponseDto toResponseDto(Appointment appointment, Schedule schedule, PatientInfoDto patientInfoDto);

}
