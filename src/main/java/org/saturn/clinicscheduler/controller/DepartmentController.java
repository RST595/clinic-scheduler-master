package org.saturn.clinicscheduler.controller;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.model.dto.request.DepartmentRequestDTO;
import org.saturn.clinicscheduler.model.dto.response.DepartmentResponseDto;
import org.saturn.clinicscheduler.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> showAllDepartments() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> addDepartment(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        return ResponseEntity.ok(service.addDepartment(departmentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteDepartment(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable Long id,
                                                                  @Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        return ResponseEntity.ok(service.updateDepartment(id, departmentRequestDTO));
    }

}
