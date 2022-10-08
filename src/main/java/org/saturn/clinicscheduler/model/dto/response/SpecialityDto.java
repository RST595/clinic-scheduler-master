package org.saturn.clinicscheduler.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityDto {

    @NotBlank(message = "Speciality name cannot be empty")
    private String name;

}
