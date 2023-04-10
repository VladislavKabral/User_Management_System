package by.kabral.usermanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class which represents user`s entity
 *
 * @author Vladislav Kabral
 */

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Field which represents user`s id
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Field which represents user`s lastname
     */
    @Column(name = "user_lastname")
    @NotEmpty(message = "Lastname must be not empty")
    @Size(min = 2, max = 20, message = "Size of lastname must be between 2 and 20 symbols")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Lastname must contain only letters")
    private String lastname;

    /**
     * Field which represents user`s firstname
     */
    @Column(name = "user_firstname")
    @NotEmpty(message = "Firstname must be not empty")
    @Size(min = 2, max = 20, message = "Size of firstname must be between 2 and 20 symbols")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я]+$", message = "Firstname must contain only letters")
    private String firstname;

    /**
     * Field which represents user`s username
     */
    @Column(name = "user_name")
    @NotEmpty(message = "Username must be not empty")
    @Size(min = 8, max = 20, message = "Size of username must be between 8 and 20 symbols")
    private String username;

    /**
     * Field which represents user`s email address
     */
    @Column(name = "user_email")
    @NotEmpty(message = "Email must be not empty")
    @Email(message = "Field \"email\" must have an email format")
    private String email;

    /**
     * Field which represents user`s date of birthday
     */
    @Column(name = "user_date_of_birthday")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirthday;

    /**
     * Field which represents user`s current state
     */
    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    private UserState state;

    /**
     * Field which represents date of creation user
     */
    @Column(name = "user_created_at")
    private LocalDateTime createdAt;

    public User(String lastname, String firstname, String username, String email, LocalDate dateOfBirthday) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.email = email;
        this.dateOfBirthday = dateOfBirthday;
    }
}
