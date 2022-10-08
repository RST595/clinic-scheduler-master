package org.saturn.clinicscheduler.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.saturn.clinicscheduler.mapper.ScheduleMapper;
import org.saturn.clinicscheduler.model.dto.request.ScheduleUnpartitionedDto;
import org.saturn.clinicscheduler.model.dto.response.ScheduleResponseDto;
import org.saturn.clinicscheduler.model.entity.Department;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Schedule;
import org.saturn.clinicscheduler.repository.DepartmentRepository;
import org.saturn.clinicscheduler.repository.DoctorRepository;
import org.saturn.clinicscheduler.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.saturn.clinicscheduler.model.DtoGeneratorTest.scheduleResponseDto;
import static org.saturn.clinicscheduler.model.DtoGeneratorTest.scheduleUnpartitionedDto;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.department;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.doctor;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.schedule;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ScheduleMapper mapper;
    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private Doctor doctor;
    private Department department;
    private Schedule schedule;
    private ScheduleUnpartitionedDto scheduleUnpartitionedDto;
    private ScheduleResponseDto scheduleResponseDto;

    @BeforeEach
    void init() {
        doctor = doctor();
        department = department();
        schedule = schedule();
        scheduleUnpartitionedDto = scheduleUnpartitionedDto();
        scheduleResponseDto = scheduleResponseDto();

        Mockito.when(doctorRepository.findById(scheduleUnpartitionedDto.getDoctorId()))
                .thenReturn(Optional.of(doctor));
    }

    @Test
    void addSchedule() {
        Mockito.when(departmentRepository.findById(scheduleUnpartitionedDto.getDepartmentId()))
                .thenReturn(Optional.of(department));
        List<Schedule> schedules = List.of(schedule);
        Mockito.when(mapper.mapToSchedules(scheduleUnpartitionedDto, doctor, department))
                .thenReturn(schedules);
        Mockito.when(scheduleRepository.saveAll(schedules))
                .thenReturn(schedules);
        List<ScheduleResponseDto> scheduleResponseDtos = List.of(scheduleResponseDto);
        Mockito.when(mapper.mapToResponseDtoList(schedules))
                .thenReturn(scheduleResponseDtos);

        assertEquals(scheduleResponseDtos, scheduleService.addSchedule(scheduleUnpartitionedDto));
    }

    @Test
    void getAllDoctorSchedules() {
        List<Schedule> schedules = List.of(schedule);
        Mockito.when(scheduleRepository.findAllByDoctor(doctor))
                .thenReturn(schedules);
        List<ScheduleResponseDto> scheduleResponseDtos = List.of(scheduleResponseDto);
        Mockito.when(mapper.mapToResponseDtoList(schedules))
                .thenReturn(scheduleResponseDtos);

        assertEquals(scheduleResponseDtos, scheduleService.getAllDoctorSchedules(doctor.getId()));
    }

    @Test
    void changeSchedule() {
        Mockito.when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        Mockito.when(doctorRepository.findById(scheduleUnpartitionedDto.getDoctorId()))
                                                        .thenReturn(Optional.of(doctor));
        Mockito.when(departmentRepository.findById(scheduleUnpartitionedDto.getDepartmentId()))
                                                        .thenReturn(Optional.of(department));
        scheduleService.changeSchedule(1L, scheduleUnpartitionedDto);
        Mockito.verify(doctorRepository, Mockito.times(1))
                                                            .findById(scheduleUnpartitionedDto.getDoctorId());
        Mockito.verify(departmentRepository, Mockito.times(1))
                                                            .findById(scheduleUnpartitionedDto.getDepartmentId());
    }

}