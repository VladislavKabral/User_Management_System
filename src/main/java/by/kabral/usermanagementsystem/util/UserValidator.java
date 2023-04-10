package by.kabral.usermanagementsystem.util;

import by.kabral.usermanagementsystem.model.User;
import by.kabral.usermanagementsystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Class which represents validation class for user`s entities
 *
 * @author Vladislav Kabral
 */
@Component
public class UserValidator implements Validator {

    /**
     * Instance of UserService class for working with database
     */
    private final UsersService usersService;

    /**
     * Constructor
     *
     * @param usersService object of UserService class
     */
    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Method which checks object for validation
     *
     * @param clazz given object
     * @return result of check (true or false)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    /**
     * Method for validate users entities
     *
     * @param target validation object
     * @param errors errors objects
     */
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (usersService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "", "User with this email address already exist");
        }

        if (usersService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "", "User with this username already exist");
        }
    }
}
