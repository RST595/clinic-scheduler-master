package org.saturn.clinicscheduler.repository;

import org.saturn.clinicscheduler.model.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long> {

    Optional<Speciality> findById(Long id);

    Optional<Speciality> findByName(String name);

}