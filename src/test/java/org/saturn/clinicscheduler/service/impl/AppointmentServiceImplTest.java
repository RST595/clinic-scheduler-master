package org.saturn.clinicscheduler.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.appointment;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.appointmentResponseDto;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.appointments;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.appointmentsDto;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.patient;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.patientInfoDto;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.schedule;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.mapper.AppointmentMapper;
import org.saturn.clinicscheduler.mapper.PatientMapper;
import org.saturn.clinicscheduler.model.DtoGeneratorTest;
import org.saturn.clinicscheduler.model.EntityGeneratorTest;
import org.saturn.clinicscheduler.model.dto.response.AppointmentResponseDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.model.entity.Appointment;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Patient;
import org.saturn.clinicscheduler.model.entity.Schedule;
import org.saturn.clinicscheduler.repository.AppointmentRepository;
import org.saturn.clinicscheduler.repository.DoctorRepository;
import org.saturn.clinicscheduler.repository.PatientRepository;
import org.saturn.clinicscheduler.repository.ScheduleRepository;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private AppointmentMapper appointmentMapper;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;
    private Schedule schedule;
    private Patient patient;
    private PatientInfoDto patientInfoDto;
    private Doctor doctor;
    private AppointmentResponseDto appointmentResponseDto;
    private List<Appointment> appointments;
    private List<AppointmentResponseDto> appointmentsDto;

    @BeforeEach
    void init() {
        appointment = appointment();
        schedule = schedule();
        patient = patient();
        patientInfoDto = patientInfoDto();
        doctor = EntityGeneratorTest.doctor();
        appointmentResponseDto = appointmentResponseDto();
        appointments = appointments();
        appointmentsDto = appointmentsDto();

    }

    @Test
    void createAppointment() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(scheduleRepository.findById(1L)).thenReturn(Optional.of(schedule));
        when(patientMapper.toInfoDto(patient)).thenReturn(patientInfoDto);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        Appointment appointmentWithoutId = appointment;
        appointmentWithoutId.setId(null);
        when(appointmentMapper.toResponseDto(appointment, schedule,
            patientMapper.toInfoDto(patient)))
            .thenReturn(appointmentResponseDto);
        appointmentService.createAppointment(1L, 1L);
        verify(scheduleRepository, times(1)).save(schedule);
        verify(appointmentRepository, times(1)).save(appointmentWithoutId);
    }

    @Test
    void cancelAppointment() {
        when(appointmentRepository.findById(appointment.getId()))
            .thenReturn(Optional.of(appointment));
        when(scheduleRepository
            .findByDoctorAndDateAndStartTime(appointment.getDoctor(),
                new Date(appointment.getDate().getTime()),
                appointment.getStartTime())).thenReturn(Optional.of(schedule));
        appointmentService.cancelAppointment(appointment.getId());
        assertTrue(schedule.getIsAvailable());
    }

    @Test
    void getAllAppointmentsByPatientWithSuccess() {

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findAllByPatient(patient)).thenReturn(appointments);

        for (Appointment a : appointments) {
            when(appointmentMapper.toResponseDto(a, schedule, patientInfoDto)).thenReturn(
                appointmentResponseDto);
            when(scheduleRepository.findByDoctorAndDateAndStartTime(a.getDoctor(),
                new Date(a.getDate().getTime()), a.getStartTime())).thenReturn(
                Optional.ofNullable(schedule));
            when(patientMapper.toInfoDto(a.getPatient())).thenReturn(patientInfoDto);
        }

        var actualResult = appointmentService
            .getAllAppointmentsByPatient(1L);

        verify(patientRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).findAllByPatient(patient);
        verify(appointmentMapper, times(3))
            .toResponseDto(any(Appointment.class), any(Schedule.class), any(PatientInfoDto.class));
        verify(scheduleRepository, times(3))
            .findByDoctorAndDateAndStartTime(any(Doctor.class), any(Date.class), any(Time.class));
        verify(patientMapper, times(3)).toInfoDto(any(Patient.class));

        assertEquals(actualResult, appointmentsDto);
    }

    @Test
    void getAllAppointmentsByPatientWithFailureWhenPatientNotFoundException() {

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findAllByPatient(patient)).thenThrow(
            ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class,
            () -> appointmentService.getAllAppointmentsByPatient(1L));

        verify(patientRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).findAllByPatient(patient);
    }

    @Test
    void getAllAppointmentsByPatientWithFailureWhenScheduleSlotNotFoundException() {

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(appointmentRepository.findAllByPatient(patient)).thenReturn(appointments);
        when(scheduleRepository.findByDoctorAndDateAndStartTime(appointment.getDoctor(),
            new Date(appointment.getDate().getTime()), appointment.getStartTime())).thenThrow(
            ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class,
            () -> appointmentService.getAllAppointmentsByPatient(1L));

        verify(patientRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).findAllByPatient(patient);
    }

    @Test
    void getAllAppointmentsByDoctorWithSuccess() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findAllByDoctor(doctor)).thenReturn(appointments);

        for (Appointment a : appointments) {
            when(appointmentMapper.toResponseDto(a, schedule, patientInfoDto)).thenReturn(
                appointmentResponseDto
            );
            when(scheduleRepository.findByDoctorAndDateAndStartTime(a.getDoctor(),
                new Date(a.getDate().getTime()), a.getStartTime())).thenReturn(
                Optional.ofNullable(schedule));
            when(patientMapper.toInfoDto(a.getPatient())).thenReturn(patientInfoDto);
        }

        var actualResult = appointmentService
            .getAllAppointmentsByDoctor(1L);

        verify(doctorRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).findAllByDoctor(doctor);
        verify(appointmentMapper, times(3))
            .toResponseDto(any(Appointment.class), any(Schedule.class), any(PatientInfoDto.class));
        verify(scheduleRepository, times(3))
            .findByDoctorAndDateAndStartTime(any(Doctor.class), any(Date.class), any(Time.class));
        verify(patientMapper, times(3)).toInfoDto(any(Patient.class));

        assertEquals(actualResult, appointmentsDto);
    }

    @Test
    void getAllAppointmentsByDoctorWithFailureWhenPatientNotFoundException() {

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findAllByDoctor(doctor)).thenThrow(
            ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class,
            () -> appointmentService.getAllAppointmentsByDoctor(1L));

        verify(doctorRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).findAllByDoctor(doctor);
    }

    @Test
    void getAllAppointmentsByDoctorWithFailureWhenScheduleSlotNotFoundException() {

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findAllByDoctor(doctor)).thenReturn(appointments);
        when(scheduleRepository.findByDoctorAndDateAndStartTime(appointment.getDoctor(),
            new Date(appointment.getDate().getTime()), appointment.getStartTime())).thenThrow(
            ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class,
            () -> appointmentService.getAllAppointmentsByDoctor(1L));

        verify(doctorRepository, times(1)).findById(1L);
        verify(appointmentRepository, times(1)).findAllByDoctor(doctor);
    }
}
