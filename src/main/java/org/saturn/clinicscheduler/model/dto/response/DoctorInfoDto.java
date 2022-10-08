package org.saturn.clinicscheduler.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.saturn.clinicscheduler.model.entity.constant.Gender;


@Data
@AllArgsConstructor
@Builder
public class DoctorInfoDto {

    private String name;
    private String birthdate;
    private Gender gender;
    private String speciality;
    private String worksFrom;
    private String phoneNumber;

}
