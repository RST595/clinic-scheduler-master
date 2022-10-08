package org.saturn.clinicscheduler.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.exception.BusyPhoneNumberException;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.exception.SpecialityAlreadyExistException;
import org.saturn.clinicscheduler.mapper.DoctorMapper;
import org.saturn.clinicscheduler.mapper.SpecialityMapper;
import org.saturn.clinicscheduler.model.dto.request.DoctorCreateDto;
import org.saturn.clinicscheduler.model.dto.response.DoctorInfoDto;
import org.saturn.clinicscheduler.model.dto.response.SpecialityDto;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Speciality;
import org.saturn.clinicscheduler.repository.DoctorRepository;
import org.saturn.clinicscheduler.repository.SpecialityRepository;
import org.saturn.clinicscheduler.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialityRepository specialityRepository;
    private final DoctorMapper doctorMapper;
    private final SpecialityMapper specialityMapper;

    @Override
    public List<Speciality> getAllSpecialities() {
        return specialityRepository.findAll();
    }

    @Override
    public List<DoctorInfoDto> getDoctorsBySpecialityId(Long id) {
        specialityRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Speciality"));
        List<DoctorInfoDto> doctors = doctorRepository.getDoctorsBySpecialityId(id).stream()
                .map(doctorMapper::mapToInfoDto)
                .collect(Collectors.toList());

        if (!doctors.isEmpty()) {
            return doctors;
        } else {
            throw new ObjectNotFoundException("Doctor");
        }
    }

    @Override
    public List<DoctorInfoDto> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(doctorMapper::mapToInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DoctorInfoDto createDoctor(DoctorCreateDto doctorCreateDto) {
        Speciality speciality = specialityRepository.findById(doctorCreateDto.getSpecialityId())
                .orElseThrow(() -> new ObjectNotFoundException("Speciality"));
        String phone = doctorCreateDto.getPhoneNumber();
        doctorRepository.getDoctorByPhoneNumber(phone).ifPresent(d -> {
            throw new BusyPhoneNumberException();
        });

        Doctor doctor = doctorMapper.mapToDoctor(doctorCreateDto, speciality);
        doctorRepository.save(doctor);

        return doctorMapper.mapToInfoDto(doctor);
    }

    @Override
    @Transactional
    public DoctorInfoDto deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Doctor"));
        doctorRepository.deleteById(id);

        return doctorMapper.mapToInfoDto(doctor);
    }

    @Override
    @Transactional
    public Speciality deleteSpeciality(Long id) {
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Speciality"));
        specialityRepository.deleteById(id);

        return speciality;
    }

    @Override
    @Transactional
    public Speciality changeSpeciality(Long id, String title) {
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Speciality"));
        if (specialityRepository.findAll().stream()
                .anyMatch(spec -> spec.getName().equals(title))) {
            throw new SpecialityAlreadyExistException();
        }
        speciality.setName(title);

        return specialityRepository.save(speciality);
    }

    @Override
    @Transactional
    public SpecialityDto createSpeciality(SpecialityDto specialityDto) {
        Speciality speciality = specialityMapper.toSpeciality(specialityDto);
        Optional<Speciality> specialityOptional = specialityRepository.findByName(speciality.getName());
        if (specialityOptional.isPresent()) {
            throw new SpecialityAlreadyExistException();
        }
        Speciality savedSpeciality = specialityRepository.save(speciality);

        return specialityMapper.toSpecialityDTO(savedSpeciality);
    }

    @Override
    @Transactional
    public DoctorInfoDto updateDoctor(Long id, DoctorCreateDto doctorCreateDto) {
        Doctor doc = doctorRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Doctor"));
        Doctor doctor = doctorMapper.mapToDoctor(doctorCreateDto, doc.getSpeciality());
        doctor.setId(id);
        doctorRepository.save(doctor);
        return doctorMapper.mapToInfoDto(doctor);
    }

}
