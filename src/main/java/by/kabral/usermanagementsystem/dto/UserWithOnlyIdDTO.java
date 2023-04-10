package by.kabral.usermanagementsystem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class which contain only user`s id for creation response
 *
 * @author Vladislav Kabral
 */

@Getter
@Setter
@NoArgsConstructor
public class UserWithOnlyIdDTO {

    /**
     * Field from User class which represents user`s id
     */
    private int id;
}
