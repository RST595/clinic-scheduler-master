package org.saturn.clinicscheduler.repository;

import org.saturn.clinicscheduler.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPassport(String passport);

    Optional<Patient> findByPhoneNumber(String phone);

}
