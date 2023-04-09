package by.kabral.usermanagementsystem.dto;

import by.kabral.usermanagementsystem.model.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class which contain user`s id, previous state and current state for creation response
 *
 * @author Vladislav Kabral
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWIthStatesDTO {

    /**
     * Field from User class which represents user`s id
     */
    private int id;

    /**
     * Field which represents user`s previous state
     */
    private UserState previousState;

    /**
     * Field which represents user`s current state
     */
    private String currentState;
}
