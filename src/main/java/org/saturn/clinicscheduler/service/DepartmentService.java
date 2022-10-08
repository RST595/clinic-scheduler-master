package org.saturn.clinicscheduler.service;

import org.saturn.clinicscheduler.model.dto.request.DepartmentRequestDTO;
import org.saturn.clinicscheduler.model.dto.response.DepartmentResponseDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto addDepartment(DepartmentRequestDTO departmentRequestDTO);

    DepartmentResponseDto deleteDepartment(Long id);

    DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO);

}
