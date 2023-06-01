package ru.effectivemobile.social_network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.social_network.model.Token;
@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    Token findTokenByRefreshToken(String refreshToken);
}
