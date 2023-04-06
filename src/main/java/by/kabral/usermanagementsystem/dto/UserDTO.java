package by.kabral.usermanagementsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    @NotEmpty(message = "Lastname must be not empty")
    @Size(min = 2, max = 20, message = "Size of lastname must be between 2 and 20 symbols")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Lastname must contain only letters")
    private String lastname;

    @NotEmpty(message = "Firstname must be not empty")
    @Size(min = 2, max = 20, message = "Size of firstname must be between 2 and 20 symbols")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Firstname must contain only letters")
    private String firstname;

    @NotEmpty(message = "Username must be not empty")
    @Size(min = 8, max = 20, message = "Size of username must be between 8 and 20 symbols")
    private String username;

    @NotEmpty(message = "Email must be not empty")
    @Email(message = "Field \"email\" must have an email format")
    private String email;

    @NotEmpty
    @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01]).(0?[1-9]|1[012]).((19|20)\\d\\d)", message = "Date of birthday must be in 'dd.MM.yyyy' format")
    private String dateOfBirthday;
}
