package org.saturn.clinicscheduler.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.saturn.clinicscheduler.model.DtoGeneratorTest.departmentRequestDTO;
import static org.saturn.clinicscheduler.model.DtoGeneratorTest.departmentResponseDto;
import static org.saturn.clinicscheduler.model.EntityGeneratorTest.department;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.saturn.clinicscheduler.exception.ObjectNotFoundException;
import org.saturn.clinicscheduler.mapper.DepartmentMapper;
import org.saturn.clinicscheduler.model.dto.request.DepartmentRequestDTO;
import org.saturn.clinicscheduler.model.dto.response.DepartmentResponseDto;
import org.saturn.clinicscheduler.model.entity.Department;
import org.saturn.clinicscheduler.repository.DepartmentRepository;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    private Department department;
    private DepartmentRequestDTO departmentRequestDTO;
    private DepartmentResponseDto departmentResponseDto;

    @Mock
    DepartmentRepository departmentRepository;
    @Mock
    DepartmentMapper departmentMapper;
    @InjectMocks
    DepartmentServiceImpl departmentService;

    @BeforeEach
    void initialize() {
        department = department();
        departmentRequestDTO = departmentRequestDTO();
        departmentResponseDto = departmentResponseDto();

    }

    @Test
    void shouldReturnNewDepartment() {
        Mockito.when(departmentMapper.mapToEntity(departmentRequestDTO)).thenReturn(department);
        Mockito.when(departmentMapper.mapToResponseDto(department)).thenReturn(departmentResponseDto);
        Mockito.when(departmentRepository.save(department)).thenReturn(department);

        DepartmentResponseDto expected = departmentService.addDepartment(departmentRequestDTO);

        assertEquals(expected, departmentResponseDto);
        Mockito.verify(departmentRepository, Mockito.times(1)).save(department);
    }

    @Test
    void shouldDeleteDepartment() {
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(department));
        Mockito.when(departmentMapper.mapToResponseDto(department)).thenReturn(departmentResponseDto);

        DepartmentResponseDto expected = departmentService.deleteDepartment(1L);

        assertEquals(expected, departmentResponseDto);
        Mockito.verify(departmentRepository, Mockito.times(1)).delete(department);
    }

    @Test
    void shouldReturnAllDepartments() {
        departmentService.getAllDepartments();
        Mockito.verify(departmentRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionOnWrongDoctorId() {
        Mockito.when(departmentRepository.findById(10L)).thenThrow(new ObjectNotFoundException("Department"));
        Assertions.assertThrows(ObjectNotFoundException.class, () -> departmentService.deleteDepartment(10L));

        Mockito.verify(departmentRepository, Mockito.times(0)).deleteById(10L);
    }
}
