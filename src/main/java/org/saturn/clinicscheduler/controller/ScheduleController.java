package org.saturn.clinicscheduler.controller;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.model.dto.request.ScheduleUnpartitionedDto;
import org.saturn.clinicscheduler.model.dto.response.ScheduleResponseDto;
import org.saturn.clinicscheduler.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping("/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> addSchedule(
            @Valid @RequestBody ScheduleUnpartitionedDto scheduleUnpartitionedDto) {
        return ResponseEntity.ok(service.addSchedule(scheduleUnpartitionedDto));
    }

    @GetMapping("/doctors/{id}/schedules")
    public ResponseEntity<List<ScheduleResponseDto>> showSchedules(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAllDoctorSchedules(id));
    }

    @PutMapping("/schedules/{id}")
    public ResponseEntity<ScheduleResponseDto> changeSchedule(
            @PathVariable Long id, @Valid @RequestBody ScheduleUnpartitionedDto scheduleUnpartitionedDto) {
        return ResponseEntity.ok(service.changeSchedule(id, scheduleUnpartitionedDto));
    }

}
