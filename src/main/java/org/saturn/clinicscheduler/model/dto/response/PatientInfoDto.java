package org.saturn.clinicscheduler.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@AllArgsConstructor
@Builder
public class PatientInfoDto {

    String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate birthdate;
    String gender;
    String chiPolicy;
    String passport;
    String phoneNumber;

}
