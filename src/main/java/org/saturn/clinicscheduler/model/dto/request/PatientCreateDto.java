package org.saturn.clinicscheduler.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientCreateDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Birthdate cannot be null")
    private LocalDate birthdate;

    @NotBlank(message = "Gender cannot be empty")
    private String gender;

    @NotBlank(message = "CHI policy number cannot be empty")
    @Pattern(regexp = "\\d{16}", message = "CHI policy number must consist of 16 digits")
    private String chiPolicy;

    @NotBlank(message = "Passport number cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Passport number must consist of 10 digits")
    private String passport;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\"#$%&'()*+,./:;<=^ _ ` { | } ~ >?@-])(?=\\S+$).{8,}$",
            message = "Password must be of 8 to 20 length and contain at least 1 uppercase, "
                    + "1 lowercase, 1 special character and 1 digit and no whitespaces")
    private String password;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "(\\+7|8)\\d{10}", message = "Phone number must start with +7 or 8 and contain 10 digits above")
    private String phoneNumber;

}
