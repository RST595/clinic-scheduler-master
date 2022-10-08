package org.saturn.clinicscheduler.service.impl;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.exception.ScheduleIsBookedException;
import org.saturn.clinicscheduler.mapper.AppointmentMapper;
import org.saturn.clinicscheduler.mapper.PatientMapper;
import org.saturn.clinicscheduler.model.dto.response.AppointmentResponseDto;
import org.saturn.clinicscheduler.model.entity.Appointment;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Patient;
import org.saturn.clinicscheduler.model.entity.Schedule;
import org.saturn.clinicscheduler.repository.AppointmentRepository;
import org.saturn.clinicscheduler.repository.DoctorRepository;
import org.saturn.clinicscheduler.repository.PatientRepository;
import org.saturn.clinicscheduler.repository.ScheduleRepository;
import org.saturn.clinicscheduler.service.AppointmentService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final PatientMapper patientMapper;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional
    public AppointmentResponseDto createAppointment(Long patientId, Long scheduleId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new ObjectNotFoundException("Patient"));
        Schedule timeSlot = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new ObjectNotFoundException("Time slot"));
        if (Boolean.FALSE.equals(timeSlot.getIsAvailable())) {
            throw new ScheduleIsBookedException();
        }
        timeSlot.setIsAvailable(false);
        scheduleRepository.save(timeSlot);
        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(timeSlot.getDoctor())
                .date(timeSlot.getDate())
                .startTime(timeSlot.getStartTime())
                .build();
        appointment = appointmentRepository.save(appointment);

        return appointmentMapper.toResponseDto(appointment, timeSlot, patientMapper.toInfoDto(patient));
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointmentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId).
                orElseThrow(() -> new ObjectNotFoundException("Patient"));
        List<Appointment> appointments = appointmentRepository.findAllByPatient(patient);

        return appointments.stream().map(a -> appointmentMapper.toResponseDto(a, scheduleRepository
                        .findByDoctorAndDateAndStartTime(a.getDoctor(), new Date(a.getDate().getTime()), a.getStartTime())
                        .orElseThrow(
                                () -> new ObjectNotFoundException("Time slot")), patientMapper.toInfoDto(patient)))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDto> getAllAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor"));
        List<Appointment> appointments = appointmentRepository.findAllByDoctor(doctor);

        return appointments.stream().map(a -> appointmentMapper.toResponseDto(a, scheduleRepository
                        .findByDoctorAndDateAndStartTime(a.getDoctor(), new Date(a.getDate().getTime()), a.getStartTime())
                        .orElseThrow(
                                () -> new ObjectNotFoundException("Time slot")), patientMapper.toInfoDto(a.getPatient())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Appointment"));
        Schedule schedule = scheduleRepository
                .findByDoctorAndDateAndStartTime(appointment.getDoctor(), new Date(appointment.getDate().getTime()),
                        appointment.getStartTime())
                .orElseThrow(() -> new ObjectNotFoundException("Time slot"));
        schedule.setIsAvailable(true);
        scheduleRepository.save(schedule);
        appointmentRepository.delete(appointment);
    }

}
