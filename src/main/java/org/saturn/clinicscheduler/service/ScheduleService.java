package org.saturn.clinicscheduler.service;

import org.saturn.clinicscheduler.model.dto.request.ScheduleUnpartitionedDto;
import org.saturn.clinicscheduler.model.dto.response.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    List<ScheduleResponseDto> addSchedule(ScheduleUnpartitionedDto scheduleUnpartitionedDto);

    List<ScheduleResponseDto> getAllDoctorSchedules(Long doctorId);

    ScheduleResponseDto changeSchedule(Long id, ScheduleUnpartitionedDto scheduleUnpartitionedDto);

}
