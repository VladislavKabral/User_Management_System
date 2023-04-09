package by.kabral.usermanagementsystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Class for creation response with errors
 *
 * @author Vladislav Kabral
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Field which represents message of error
     */
    private String message;

    /**
     * Field which represents time of creation error
     */
    private LocalDateTime time;
}
