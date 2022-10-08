package org.saturn.clinicscheduler.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponseDto {

    private String city;
    private String district;
    private String address;
    private String phoneNumber;

}
