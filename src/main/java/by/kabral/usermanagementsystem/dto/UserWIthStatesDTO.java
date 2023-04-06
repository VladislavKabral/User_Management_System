package by.kabral.usermanagementsystem.dto;

import by.kabral.usermanagementsystem.model.UserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWIthStatesDTO {

    private int id;

    private UserState previousState;

    private String currentState;
}
