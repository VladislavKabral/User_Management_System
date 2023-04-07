package by.kabral.usermanagementsystem.controller;

import by.kabral.usermanagementsystem.dto.UserWIthStatesDTO;
import by.kabral.usermanagementsystem.dto.UserWithOnlyIdDTO;
import by.kabral.usermanagementsystem.dto.UserDTO;
import by.kabral.usermanagementsystem.model.ImagineRequest;
import by.kabral.usermanagementsystem.model.User;
import by.kabral.usermanagementsystem.model.UserState;
import by.kabral.usermanagementsystem.service.UsersService;
import by.kabral.usermanagementsystem.util.ErrorResponse;
import by.kabral.usermanagementsystem.util.UserException;
import by.kabral.usermanagementsystem.util.UserValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    private final ModelMapper modelMapper;

    private final UserValidator userValidator;

    private final ImagineRequest imagineRequest;

    private static final int REQUEST_TIME = 7000;

    @Autowired
    public UsersController(UsersService usersService, ModelMapper modelMapper, UserValidator userValidator, ImagineRequest imagineRequest) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
        this.imagineRequest = imagineRequest;
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable("id") int id) throws UserException {
        imagineRequest.wait(REQUEST_TIME);

        return convertToUserDTO(usersService.findById(id));
    }

    @PostMapping
    public UserWithOnlyIdDTO createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult)
            throws UserException {

        imagineRequest.wait(REQUEST_TIME);

        User user = convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            for (FieldError error: bindingResult.getFieldErrors()) {
                message.append(error.getDefaultMessage()).append(". ");
            }

            throw new UserException(message.toString());
        }

        user.setDateOfBirthday(convertToLocalDate(userDTO.getDateOfBirthday()));
        usersService.create(user);
        return convertToResponseUserWithOnlyId(usersService.findlastCreatedUser());
    }

    @PatchMapping("/{id}")
    public UserWIthStatesDTO updateUserState(@RequestBody UserWIthStatesDTO userWIthStatesDTO, @PathVariable("id") int id) throws UserException {

        imagineRequest.wait(REQUEST_TIME);

        try {
            UserState.valueOf(userWIthStatesDTO.getCurrentState());
        } catch (IllegalArgumentException e) {
            throw new UserException(userWIthStatesDTO.getCurrentState() + " state is invalid");
        }

        User user = usersService.findById(id);
        UserState previousUserState = user.getState();

        user.setState(UserState.valueOf(userWIthStatesDTO.getCurrentState()));

        usersService.update(user, id);

        return new UserWIthStatesDTO(id, previousUserState, userWIthStatesDTO.getCurrentState());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserWithOnlyIdDTO convertToResponseUserWithOnlyId(User user) {
        return modelMapper.map(user, UserWithOnlyIdDTO.class);
    }

    private LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }
}
