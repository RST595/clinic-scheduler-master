package org.saturn.clinicscheduler.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorCreateDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Birthdate cannot be empty")
    private String birthdate;

    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    @NotNull(message = "Speciality cannot be null")
    @Min(value = 1, message = "Specialities' IDs must be > 0")
    private Long specialityId;

    @NotBlank(message = "Year cannot be empty")
    private String worksFrom;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "(\\+7|8)\\d{10}", message = "Phone number must start with +7 or 8 and contain 10 digits above")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,./:;<=^ _ ` { | } ~ >?@-])(?=\\S+$).{8,}$",
            message = "Password must be of 8 to 20 length and contain at least 1 uppercase, "
                    + "1 lowercase, 1 special character and 1 digit and no whitespaces")
    private String password;

}
