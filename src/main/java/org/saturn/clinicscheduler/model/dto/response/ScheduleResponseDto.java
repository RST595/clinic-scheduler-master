package org.saturn.clinicscheduler.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponseDto {

    private String doctorName;
    private String departmentAddress;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Integer cabinet;
    private Boolean isAvailable;

}
