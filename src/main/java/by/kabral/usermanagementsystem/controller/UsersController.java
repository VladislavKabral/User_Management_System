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

/**
 * Class which represents controller for user`s entities
 *
 * @author Vladislav Kabral
 */

@RestController
@RequestMapping("/users")
public class UsersController {

    /**
     * Instance of UsersService class for relationships with database
     */
    private final UsersService usersService;


    /**
     * Instance of ModelMapper class for mapping objects
     */
    private final ModelMapper modelMapper;


    /**
     * Instance of UserValidator class for validation user`s entities
     */
    private final UserValidator userValidator;


    /**
     * Instance of ImagineRequest class for creating delay
     */
    private final ImagineRequest imagineRequest;


    /**
     * Constant which represents count of delay time
     */
    private static final int REQUEST_TIME = 7000;


    /**
     * Constructor
     *
     * @param imagineRequest object of UsersService class for relationships with database
     * @param modelMapper object of ModelMapper class for mapping objects
     * @param userValidator object of UserValidator class for validation user`s entities
     * @param usersService object of ImagineRequest class for creating delay
     */
    @Autowired
    public UsersController(UsersService usersService, ModelMapper modelMapper, UserValidator userValidator, ImagineRequest imagineRequest) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.userValidator = userValidator;
        this.imagineRequest = imagineRequest;
    }


    /**
     * Method which returns user by id
     *
     * @param id user`s id
     * @throws UserException if user won`t find
     *
     * @return object of UserDTO class with user`s fields
     */
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable("id") int id) throws UserException {
        imagineRequest.wait(REQUEST_TIME);

        return convertToUserDTO(usersService.findById(id));
    }


    /**
     * Method which creates new user
     *
     * @param userDTO object with data of new user
     * @param bindingResult object with validation errors
     * @throws UserException if user have invalid fields
     *
     * @return id of new user in database
     */
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


    /**
     * Method which updates user`s state
     *
     * @param id user`s id
     * @param userWIthStatesDTO object with new user`s state
     * @throws UserException if user won`t find or given state is invalid
     *
     * @return object with user`s id, previous state and current state
     */
    @PatchMapping("/{id}")
    public UserWIthStatesDTO updateUserState(@RequestBody UserWIthStatesDTO userWIthStatesDTO, @PathVariable("id") int id) throws UserException {

        imagineRequest.wait(REQUEST_TIME);

        try {
            UserState.valueOf(userWIthStatesDTO.getCurrentState());
        } catch (IllegalArgumentException e) {
            throw new UserException("'" + userWIthStatesDTO.getCurrentState() + "' state is invalid");
        }

        User user = usersService.findById(id);
        UserState previousUserState = user.getState();

        user.setState(UserState.valueOf(userWIthStatesDTO.getCurrentState()));

        usersService.update(user, id);

        return new UserWIthStatesDTO(id, previousUserState, userWIthStatesDTO.getCurrentState());
    }


    /**
     * Method which handles exceptions in UsersController
     *
     * @param exception object of UserException class which contains exception
     *
     * @return ResponseEntity with message about exception, time of generation exception and HTTP status
     */
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(UserException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
    }


    /**
     * Method which converts object of User class to object of UserDTO class
     *
     * @param user object of User class
     *
     * @return object of UserDTO class with data of given user
     */
    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }


    /**
     * Method which converts object of UserDTO class to object of User class
     *
     * @param userDTO object of UserDTO class
     *
     * @return object of User class with data of given userDTO
     */
    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


    /**
     * Method which converts object of User class to object UserWithOnlyIdDTO class
     *
     * @param user object of User class
     *
     * @return object of UserWithOnlyIdDTO class with id of given user
     */
    private UserWithOnlyIdDTO convertToResponseUserWithOnlyId(User user) {
        return modelMapper.map(user, UserWithOnlyIdDTO.class);
    }


    /**
     * Method which converts date from String to LocalDate
     *
     * @param date date in String format
     *
     * @return object of LocalDate class with given date
     */
    private LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(date, formatter);
    }
}
