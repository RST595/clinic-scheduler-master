package org.saturn.clinicscheduler.controller;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.model.dto.request.DoctorCreateDto;
import org.saturn.clinicscheduler.model.dto.response.DoctorInfoDto;
import org.saturn.clinicscheduler.model.dto.response.SpecialityDto;
import org.saturn.clinicscheduler.model.entity.Speciality;
import org.saturn.clinicscheduler.service.DoctorService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/specialities")
    public ResponseEntity<List<Speciality>> getSpecialities() {
        return ResponseEntity.ok(doctorService.getAllSpecialities());
    }

    @GetMapping("/specialities/{id}/doctors")
    public ResponseEntity<List<DoctorInfoDto>> getDoctorsBySpeciality(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialityId(id));
    }
    
    @PutMapping("/specialities/{id}")
    public ResponseEntity<Speciality> changeSpeciality(@PathVariable Long id,
                                                       @RequestParam String title) {
        return ResponseEntity.ok(doctorService.changeSpeciality(id, title));
    }

    @DeleteMapping("/specialities/{id}")
    public ResponseEntity<Speciality> deleteSpeciality(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.deleteSpeciality(id));
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorInfoDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping("/doctors")
    public ResponseEntity<DoctorInfoDto> createDoctor(@Valid @RequestBody DoctorCreateDto doctorCreateDto) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorCreateDto));
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<DoctorInfoDto> deleteDoctor(@PathVariable Long id) {
        return ResponseEntity.ok((doctorService.deleteDoctor(id)));
    }

    @PostMapping("/specialities")
    public ResponseEntity<SpecialityDto> createSpeciality(@Valid @RequestBody SpecialityDto specialityDto){
        return ResponseEntity.ok(doctorService.createSpeciality(specialityDto));
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<DoctorInfoDto> updateDoctor(@Valid @RequestBody DoctorCreateDto doctorCreateDto,
                                                      @PathVariable Long id){
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorCreateDto));
    }

}
