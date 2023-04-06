package by.kabral.usermanagementsystem.util;

import by.kabral.usermanagementsystem.model.User;
import by.kabral.usermanagementsystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

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
