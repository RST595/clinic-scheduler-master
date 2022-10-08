package org.saturn.clinicscheduler.repository;

import org.saturn.clinicscheduler.model.entity.Appointment;
import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient(Patient patient);

    List<Appointment> findAllByDoctor(Doctor doctor);

}
