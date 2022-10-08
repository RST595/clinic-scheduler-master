package org.saturn.clinicscheduler.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleUnpartitionedDto {

    @NotNull(message = "Doctor cannot be null")
    @Min(value = 1, message = "Doctors' IDs must be > 0")
    private Long doctorId;

    @NotNull(message = "Department cannot be null")
    @Min(value = 1, message = "Departments' IDs must be > 0")
    private Long departmentId;

    @NotNull(message = "Date cannot be null")
    private Date date;

    @NotNull(message = "Start time cannot be null")
    private Time startTime;

    @NotNull(message = "End time cannot be null")
    private Time endTime;

    @NotNull(message = "Cabinet cannot be null")
    @Min(value = 1, message = "Cabinet number must be > 0")
    private Integer cabinet;

}
