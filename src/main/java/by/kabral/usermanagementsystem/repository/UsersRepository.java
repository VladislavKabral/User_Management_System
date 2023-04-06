package by.kabral.usermanagementsystem.repository;

import by.kabral.usermanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    User findFirstByOrderByCreatedAtDesc();

    User findByUsername(String username);

    User findByEmail(String email);
}
