package org.saturn.clinicscheduler.repository;

import org.saturn.clinicscheduler.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> getDoctorsBySpecialityId(Long id);

    Optional<Doctor> getDoctorByPhoneNumber(String phone);

    Optional<Doctor> findById(Long id);

}