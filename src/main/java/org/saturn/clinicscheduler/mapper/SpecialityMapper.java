package org.saturn.clinicscheduler.mapper;

import org.mapstruct.Mapper;
import org.saturn.clinicscheduler.model.dto.response.SpecialityDto;
import org.saturn.clinicscheduler.model.entity.Speciality;

@Mapper(componentModel = "spring")
public interface SpecialityMapper {

    Speciality toSpeciality(SpecialityDto specialityDto);

    SpecialityDto toSpecialityDTO(Speciality speciality);
}
