package org.saturn.clinicscheduler.controller;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.model.dto.response.AppointmentResponseDto;
import org.saturn.clinicscheduler.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAnAppointment(@NonNull @RequestParam Long patientId,
                                                                      @NonNull @RequestParam Long scheduleId) {
        return ResponseEntity.ok(appointmentService.createAppointment(patientId, scheduleId));
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> getPatientAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsByPatient(id));
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<List<AppointmentResponseDto>> getDoctorAppointments(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAllAppointmentsByDoctor(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("The appointment was canceled");
    }

}
