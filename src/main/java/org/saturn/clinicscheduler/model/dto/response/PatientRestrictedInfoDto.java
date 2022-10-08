package org.saturn.clinicscheduler.model.dto.response;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PatientRestrictedInfoDto {

    String name;
    LocalDate birthdate;
    String gender;
    String chiPolicy;
    String phoneNumber;

}
