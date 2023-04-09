package by.kabral.usermanagementsystem.repository;

import by.kabral.usermanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Class which represents repository for user`s entity
 *
 * @author Vladislav Kabral
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    /**
     * Method which finds last created user to database
     *
     * @return last created user
     */
    User findFirstByOrderByCreatedAtDesc();

    /**
     * Method which finds user by username
     *
     * @param username user`s username
     * @return user with given username
     */
    User findByUsername(String username);

    /**
     * Method which finds user by email
     *
     * @param email user`s email address
     * @return user with given email address
     */
    User findByEmail(String email);
}
