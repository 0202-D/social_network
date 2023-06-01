package ru.effectivemobile.social_network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.social_network.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);

    User findUserById(Long id);

    boolean existsByEmail(String email);
}
