package org.saturn.clinicscheduler.service.impl;

import lombok.RequiredArgsConstructor;
import org.saturn.clinicscheduler.exception.DepartmentHasAlreadyExistedException;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.mapper.DepartmentMapper;
import org.saturn.clinicscheduler.model.dto.request.DepartmentRequestDTO;
import org.saturn.clinicscheduler.model.dto.response.DepartmentResponseDto;
import org.saturn.clinicscheduler.model.entity.Department;
import org.saturn.clinicscheduler.repository.DepartmentRepository;
import org.saturn.clinicscheduler.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentMapper.mapToResponseDtoList(departmentRepository.findAll());
    }

    @Override
    public DepartmentResponseDto addDepartment(DepartmentRequestDTO departmentRequestDTO) {
        Department department = departmentMapper.mapToEntity(departmentRequestDTO);
        departmentRepository.save(department);

        return departmentMapper.mapToResponseDto(department);
    }

    @Override
    public DepartmentResponseDto deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new ObjectNotFoundException("Department"));
        departmentRepository.delete(department);

        return departmentMapper.mapToResponseDto(department);
    }

    @Override
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDTO departmentRequestDTO) {
        Department updateDepartment = departmentRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Department"));
        if (departmentRepository.findAll().stream().anyMatch(department -> department.equals(departmentMapper
                .mapToEntity(departmentRequestDTO)))) {
            throw new DepartmentHasAlreadyExistedException();
        }
        updateDepartment.setCity(departmentRequestDTO.getCity());
        updateDepartment.setDistrict(departmentRequestDTO.getDistrict());
        updateDepartment.setAddress(departmentRequestDTO.getAddress());
        updateDepartment.setPhoneNumber(departmentRequestDTO.getPhoneNumber());

        return departmentMapper.mapToResponseDto(departmentRepository.save(updateDepartment));
    }

}
