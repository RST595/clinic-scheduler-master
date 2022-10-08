package org.saturn.clinicscheduler.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentRequestDTO {

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "District cannot be empty")
    private String district;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "(\\+7|8)\\d{10}", message = "Phone number must start with +7 or 8 and contain 10 digits above")
    private String phoneNumber;

}
