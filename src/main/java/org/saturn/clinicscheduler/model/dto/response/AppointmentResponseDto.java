package org.saturn.clinicscheduler.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponseDto {

    private String doctorName;
    private String departmentAddress;
    private Integer cabinet;
    private PatientInfoDto patientInfo;
    private Date date;
    private Time startTime;

}
