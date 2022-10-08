package org.saturn.clinicscheduler.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.mapper.DoctorMapper;
import org.saturn.clinicscheduler.mapper.SpecialityMapper;
import org.saturn.clinicscheduler.model.DtoGeneratorTest;
import org.saturn.clinicscheduler.model.dto.request.DoctorCreateDto;
import org.saturn.clinicscheduler.model.dto.response.DoctorInfoDto;
import org.saturn.clinicscheduler.model.dto.response.SpecialityDto;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Speciality;
import org.saturn.clinicscheduler.model.entity.constant.Gender;
import org.saturn.clinicscheduler.repository.DoctorRepository;
import org.saturn.clinicscheduler.repository.SpecialityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.saturn.clinicscheduler.model.DtoGeneratorTest.doctorCreateDto;
import static org.saturn.clinicscheduler.model.DtoGeneratorTest.doctorInfoDto;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.doctor;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.speciality;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceImplTest {

    private Doctor doctor;
    private DoctorInfoDto doctorInfoDto;
    private DoctorCreateDto doctorCreateDto;
    private Speciality speciality;
    private SpecialityDto specialityDto;

    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    SpecialityRepository specialityRepository;
    @Mock
    SpecialityMapper specialityMapper;
    @InjectMocks
    DoctorServiceImpl doctorService;

    @BeforeEach
    void initialize() {
        doctor = doctor();
        doctorInfoDto = doctorInfoDto();
        doctorCreateDto = doctorCreateDto();
        speciality = speciality();
        specialityDto = DtoGeneratorTest.specialityDto();
    }

    void assertEqualsRequestAndResponse(DoctorCreateDto request, DoctorInfoDto response) {
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getBirthdate(), response.getBirthdate());
        assertEquals(Gender.valueOf(request.getGender()), response.getGender());
        assertEquals(specialityRepository.findById(request.getSpecialityId()).get().getName(),
            response.getSpeciality());
        assertEquals(request.getWorksFrom(), response.getWorksFrom());
        assertEquals(request.getPhoneNumber(), response.getPhoneNumber());
    }

    @Test
    void createDoctor() {
        Mockito.when(doctorRepository.save(doctor)).thenReturn(doctor);
        Mockito.when(doctorMapper.mapToDoctor(doctorCreateDto, speciality)).thenReturn(doctor);
        Mockito.when(doctorMapper.mapToInfoDto(doctor)).thenReturn(doctorInfoDto);
        Mockito.when(specialityRepository.findById(1L)).thenReturn(Optional.ofNullable(speciality));
        DoctorInfoDto result = doctorService.createDoctor(doctorCreateDto);

        assertEqualsRequestAndResponse(doctorCreateDto, result);
        Mockito.verify(doctorRepository, Mockito.times(1)).save(doctor);
    }

    @Test
    void getListOfDoctors() {
        doctorService.getAllDoctors();

        Mockito.verify(doctorRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getListOfDoctorsOfChosenSpeciality() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);

        Mockito.when(specialityRepository.findById(1L)).thenReturn(Optional.ofNullable(speciality));
        Mockito.when(doctorRepository.getDoctorsBySpecialityId(1L)).thenReturn(doctors);
        Mockito.when(doctorMapper.mapToInfoDto(doctor)).thenReturn(doctorInfoDto);

        List<DoctorInfoDto> result = doctorService.getDoctorsBySpecialityId(1L);
        for (DoctorInfoDto d : result) {
            assertEquals(speciality.getName(), d.getSpeciality());
        }

        Mockito.verify(doctorRepository, Mockito.times(1)).getDoctorsBySpecialityId(1L);
    }

    @Test
    void deleteDoctorById() {
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.ofNullable(doctor));
        doctorService.deleteDoctor(1L);

        Mockito.verify(doctorRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void throwExceptionOnWrongDoctorId() {
        Mockito.when(doctorRepository.findById(10L))
            .thenThrow(new ObjectNotFoundException("Doctor"));
        Assertions.assertThrows(ObjectNotFoundException.class,
            () -> doctorService.deleteDoctor(10L));

        Mockito.verify(doctorRepository, Mockito.times(0)).deleteById(10L);
    }

    @Test
    void changeSpecialty() {
        Mockito.when(specialityRepository.findById(1L)).thenReturn(Optional.ofNullable(speciality));
        doctorService.changeSpeciality(1L, "Терапевт");
        Mockito.verify(specialityRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(specialityRepository, Mockito.times(1)).findAll();
        Mockito.when(specialityRepository.findById(2L)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () ->
            doctorService.changeSpeciality(2L, "Терапевт"));
    }

    @Test
    void createSpecialtyWithSuccess() {
        Mockito.when(specialityRepository.save(speciality)).thenReturn(speciality);
        Mockito.when(specialityMapper.toSpeciality(specialityDto)).thenReturn(speciality);
        Mockito.when(specialityMapper.toSpecialityDTO(speciality)).thenReturn(specialityDto);

        var actualResult = doctorService.createSpeciality(specialityDto);

        assertEquals(actualResult, specialityDto);

        Mockito.verify(specialityRepository, Mockito.times(1))
            .save(speciality);
    }
}