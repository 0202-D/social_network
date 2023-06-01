package ru.effectivemobile.social_network.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.social_network.model.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(value = "select p from  Post p where p.user.id=:userId order by p.createdAt desc")
    List<Post> findAllByUserId(long userId, PageRequest of);
}
