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

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User findById(int id) throws UserException {
        Optional<User> user = usersRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserException("User with id: '" + id + "' not found");
        }

        return user.get();
    }

    public User findlastCreatedUser() throws UserException {
        Optional<User> user = Optional.ofNullable(usersRepository.findFirstByOrderByCreatedAtDesc());

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        return user.get();
    }

    public User findByEmail(String email) {
        Optional<User> user = Optional.ofNullable(usersRepository.findByEmail(email));

        return user.orElse(null);
    }

    public User findByUsername(String username) {
        Optional<User> user = Optional.ofNullable(usersRepository.findByUsername(username));

        return user.orElse(null);
    }

    @Transactional
    public void create(User user) {
        user.setState(UserState.ONLINE);
        user.setCreatedAt(LocalDateTime.now());
        usersRepository.save(user);
    }

    @Transactional
    public void update(User user, int id) {
        user.setId(id);
        usersRepository.save(user);
    }
}
