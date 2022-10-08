package org.saturn.clinicscheduler.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.saturn.clinicscheduler.model.dto.request.DepartmentRequestDTO;
import org.saturn.clinicscheduler.model.dto.response.DepartmentResponseDto;
import org.saturn.clinicscheduler.model.entity.Department;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(target = "departmentResponseDTO", source = "department")
    List<DepartmentResponseDto> mapToResponseDtoList(List<Department> department);

    DepartmentResponseDto mapToResponseDto(Department department);

    Department mapToEntity(DepartmentRequestDTO department);

}
