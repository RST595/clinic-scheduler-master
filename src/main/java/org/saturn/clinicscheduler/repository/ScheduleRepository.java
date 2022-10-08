package org.saturn.clinicscheduler.repository;

import org.saturn.clinicscheduler.model.entity.Doctor;
import org.saturn.clinicscheduler.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByDoctor(Doctor doctor);

    Optional<Schedule> findByDoctorAndDateAndStartTime(Doctor doctor, Date date, Time startTime);

}
