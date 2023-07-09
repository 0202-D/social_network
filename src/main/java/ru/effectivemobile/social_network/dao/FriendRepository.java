package ru.effectivemobile.social_network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.social_network.model.Friend;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {

    Optional<Friend> findBySecondUserIdAndFirstUserId(Long id, long userId);

}

