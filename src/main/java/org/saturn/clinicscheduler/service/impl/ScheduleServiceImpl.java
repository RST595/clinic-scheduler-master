package org.saturn.clinicscheduler.service.impl;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.exception.ScheduleIsBookedException;
import org.saturn.clinicscheduler.mapper.ScheduleMapper;
import org.saturn.clinicscheduler.model.dto.request.ScheduleUnpartitionedDto;
import org.saturn.clinicscheduler.model.dto.response.ScheduleResponseDto;
import org.saturn.clinicscheduler.model.entity.Department;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Schedule;
import org.saturn.clinicscheduler.repository.DepartmentRepository;
import org.saturn.clinicscheduler.repository.DoctorRepository;
import org.saturn.clinicscheduler.repository.ScheduleRepository;
import org.saturn.clinicscheduler.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final ScheduleMapper mapper;

    @Override
    @Transactional
    public List<ScheduleResponseDto> addSchedule(ScheduleUnpartitionedDto scheduleUnpartitionedDto) {
        Doctor doctor = doctorRepository.findById(scheduleUnpartitionedDto.getDoctorId())
                .orElseThrow(() -> new ObjectNotFoundException("Doctor"));
        Department department = departmentRepository.findById(scheduleUnpartitionedDto.getDepartmentId())
                .orElseThrow(() -> new ObjectNotFoundException("Department"));
        List<Schedule> schedules = mapper.mapToSchedules(scheduleUnpartitionedDto, doctor, department);
        schedules = scheduleRepository.saveAll(schedules);

        return mapper.mapToResponseDtoList(schedules);
    }

    @Override
    public List<ScheduleResponseDto> getAllDoctorSchedules(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor"));
        List<Schedule> schedules = scheduleRepository.findAllByDoctor(doctor);
        schedules = schedules.stream()
                .filter(s -> s.getDate().toLocalDate().isAfter(LocalDate.now())
                        || s.getDate().toLocalDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());

        return mapper.mapToResponseDtoList(schedules);
    }

    @Override
    @Transactional
    public ScheduleResponseDto changeSchedule(Long id, ScheduleUnpartitionedDto scheduleUnpartitionedDto) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Schedule slot"));
        if (Boolean.FALSE.equals(schedule.getIsAvailable())) {
            throw new ScheduleIsBookedException();
        }
        Doctor doctor = doctorRepository.findById(scheduleUnpartitionedDto.getDoctorId())
                .orElseThrow(() -> new ObjectNotFoundException("Doctor"));
        Department department = departmentRepository.findById(scheduleUnpartitionedDto.getDepartmentId())
                .orElseThrow(() -> new ObjectNotFoundException("Department"));
        schedule = mapper.mapToSingleSchedule(scheduleUnpartitionedDto, doctor, department, schedule);

        return mapper.mapToResponseDto(scheduleRepository.save(schedule));
    }

}
