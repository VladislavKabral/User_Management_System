package by.kabral.usermanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_lastname")
    @NotEmpty(message = "Lastname must be not empty")
    @Size(min = 2, max = 20, message = "Size of lastname must be between 2 and 20 symbols")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Lastname must contain only letters")
    private String lastname;

    @Column(name = "user_firstname")
    @NotEmpty(message = "Firstname must be not empty")
    @Size(min = 2, max = 20, message = "Size of firstname must be between 2 and 20 symbols")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Firstname must contain only letters")
    private String firstname;

    @Column(name = "user_name")
    @NotEmpty(message = "Username must be not empty")
    @Size(min = 8, max = 20, message = "Size of username must be between 8 and 20 symbols")
    private String username;

    @Column(name = "user_email")
    @NotEmpty(message = "Email must be not empty")
    @Email(message = "Field \"email\" must have an email format")
    private String email;

    @Column(name = "user_date_of_birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirthday;

    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState state;

    @Column(name = "user_created_at")
    private LocalDateTime createdAt;
}
