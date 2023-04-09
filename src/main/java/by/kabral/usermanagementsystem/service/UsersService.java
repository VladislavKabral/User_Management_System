package by.kabral.usermanagementsystem.service;

import by.kabral.usermanagementsystem.model.User;
import by.kabral.usermanagementsystem.model.UserState;
import by.kabral.usermanagementsystem.repository.UsersRepository;
import by.kabral.usermanagementsystem.util.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Class which represents service for user`s entities
 *
 * @author Vladislav Kabral
 */

@Service
@Transactional(readOnly = true)
public class UsersService {

    /**
     * Instance of UserRepository for working with database
     */
    private final UsersRepository usersRepository;

    /**
     * Constructor
     *
     * @param usersRepository object of UsersRepository class
     */
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Method which returns user by id
     *
     * @param id user`s id
     * @return user with given id
     * @throws UserException if user won`t find
     */
    public User findById(int id) throws UserException {
        Optional<User> user = usersRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User with id: '" + id + "' not found");
        }

        return user.get();
    }

    /**
     * Method which finds last created user to database
     *
     * @return last created user in database
     * @throws UserException if user won`t find
     */
    public User findlastCreatedUser() throws UserException {
        Optional<User> user = Optional.ofNullable(usersRepository.findFirstByOrderByCreatedAtDesc());

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return user.get();
    }

    /**
     * Method which finds user by email
     *
     * @param email user`s email address
     * @return user with given email address or null if user won`t find
     */
    public User findByEmail(String email) {
        Optional<User> user = Optional.ofNullable(usersRepository.findByEmail(email));

        return user.orElse(null);
    }

    /**
     * Method which finds user by username
     *
     * @param username user`s username
     * @return user with given username or null if user won`t find
     */
    public User findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(usersRepository.findByUsername(username));

        return user.orElse(null);
    }

    /**
     * Method which creates new user
     *
     * @param user object of User class for creation new user
     */
    @Transactional
    public void create(User user) {
        user.setState(UserState.ONLINE);
        user.setCreatedAt(LocalDateTime.now());
        usersRepository.save(user);
    }

    /**
     * Method which updates user`s state
     *
     * @param user object of User class with updated fields
     * @param id user`s id
     */
    @Transactional
    public void update(User user, int id) {
        user.setId(id);
        usersRepository.save(user);
    }
}
