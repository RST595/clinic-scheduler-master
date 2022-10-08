package org.saturn.clinicscheduler.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.saturn.clinicscheduler.model.dto.response.AppointmentResponseDto;
import org.saturn.clinicscheduler.model.dto.response.PatientInfoDto;
import org.saturn.clinicscheduler.model.entity.Appointment;
import org.saturn.clinicscheduler.model.entity.Department;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Patient;
import org.saturn.clinicscheduler.model.entity.Schedule;
import org.saturn.clinicscheduler.model.entity.Speciality;
import org.saturn.clinicscheduler.model.entity.constant.Gender;

public class EntityGeneratorTest {

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.of(1975, 12, 12);
    private static final Gender DEFAULT_GENDER = Gender.муж;
    private static final String DEFAULT_PASSWORD = "Doctor123*";
    private static final String CHI_POLICY = "1234567890123456";
    private static final int CABINET = 300;
    private static final String DEPARTMENT_CITY = "Санкт-Петербург";
    private static final String DEPARTMENT_DISTRICT = "Красногвардейский";
    private static final String DEPARTMENT_ADDRESS = "Ленская, 11";
    private static final String DEPARTMENT_NUMBER = "88005553535";
    private static final long DEFAULT_FIRST_ID = 1L;
    private static final long DEFAULT_SECOND_ID = 2L;
    private static final long DEFAULT_THIRD_ID = 2L;
    private static final String SPECIALITY_NAME = "Хирург";
    private static final String DEFAULT_NAME = "Иванов Иван Иванович";
    private static final LocalDate DOCTOR_WORKS_FROM = LocalDate.of(1999, 11, 11);
    private static final String DOCTOR_NUMBER = "81111111111";
    private static final String PASSPORT = "1234567890";
    private static final String PATIENT_NUMBER = "88009000000";
    private static final Time START_TIME = Time.valueOf("12:00:00");
    private static final Time END_TIME = Time.valueOf("13:00:00");
    private static final java.sql.Date SCHEDULE_DATE = java.sql.Date.valueOf("2022-12-12");

    public static Department department() {
        return Department.builder()
                .id(DEFAULT_FIRST_ID)
                .city(DEPARTMENT_CITY)
                .district(DEPARTMENT_DISTRICT)
                .address(DEPARTMENT_ADDRESS)
                .phoneNumber(DEPARTMENT_NUMBER)
                .build();
    }

    public static Speciality speciality() {
        return Speciality.builder()
                .id(DEFAULT_FIRST_ID)
                .name(SPECIALITY_NAME)
                .build();
    }

    public static Doctor doctor() {
        return Doctor.builder()
                .id(DEFAULT_FIRST_ID)
                .name(DEFAULT_NAME)
                .birthdate(DEFAULT_BIRTHDATE)
                .gender(DEFAULT_GENDER)
                .speciality(speciality())
                .worksFromYear(DOCTOR_WORKS_FROM)
                .phoneNumber(DOCTOR_NUMBER)
                .password(DEFAULT_PASSWORD)
                .build();
    }

    public static Patient patient() {
        return Patient.builder()
                .id(DEFAULT_FIRST_ID)
                .name(DEFAULT_NAME)
                .birthdate(Date.from(DEFAULT_BIRTHDATE.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))
                .gender(DEFAULT_GENDER.toString())
                .chiPolicy(CHI_POLICY)
                .passport(PASSPORT)
                .phoneNumber(PATIENT_NUMBER)
                .password(DEFAULT_PASSWORD)
                .build();
    }

    public static PatientInfoDto patientInfoDto() {
        return PatientInfoDto.builder()
                .name(DEFAULT_NAME)
                .birthdate(DEFAULT_BIRTHDATE)
                .gender(DEFAULT_GENDER.toString())
                .chiPolicy(CHI_POLICY)
                .passport(PASSPORT)
                .phoneNumber(PATIENT_NUMBER)
                .build();
    }

    public static Schedule schedule() {
        return Schedule.builder()
                .id(DEFAULT_FIRST_ID)
                .doctor(doctor())
                .department(department())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .cabinet(CABINET)
                .isAvailable(true)
                .build();
    }

    public static Appointment appointment() {
        return Appointment.builder()
                .id(DEFAULT_FIRST_ID)
                .patient(patient())
                .doctor(doctor())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build();
    }

    public static AppointmentResponseDto appointmentResponseDto() {
        return AppointmentResponseDto.builder()
                .doctorName(DEFAULT_NAME)
                .departmentAddress(DEPARTMENT_ADDRESS)
                .cabinet(1)
                .patientInfo(patientInfoDto())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build();
    }

    public static List<Appointment> appointments() {
        return List.of(
            Appointment.builder()
                .id(DEFAULT_FIRST_ID)
                .patient(patient())
                .doctor(doctor())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build(),
            Appointment.builder()
                .id(DEFAULT_SECOND_ID)
                .patient(patient())
                .doctor(doctor())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build(),
            Appointment.builder()
                .id(DEFAULT_THIRD_ID)
                .patient(patient())
                .doctor(doctor())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build()
        );
    }

    public static List<AppointmentResponseDto> appointmentsDto() {
        return List.of(
            AppointmentResponseDto.builder()
                .doctorName(DEFAULT_NAME)
                .departmentAddress(DEPARTMENT_ADDRESS)
                .cabinet(1)
                .patientInfo(patientInfoDto())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build(),
            AppointmentResponseDto.builder()
                .doctorName(DEFAULT_NAME)
                .departmentAddress(DEPARTMENT_ADDRESS)
                .cabinet(1)
                .patientInfo(patientInfoDto())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build(),
            AppointmentResponseDto.builder()
                .doctorName(DEFAULT_NAME)
                .departmentAddress(DEPARTMENT_ADDRESS)
                .cabinet(1)
                .patientInfo(patientInfoDto())
                .date(SCHEDULE_DATE)
                .startTime(START_TIME)
                .build()
        );
    }
}
