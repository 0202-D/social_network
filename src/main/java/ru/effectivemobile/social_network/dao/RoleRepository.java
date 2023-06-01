package ru.effectivemobile.social_network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.effectivemobile.social_network.model.Role;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findRoleByName(String roleName);

}
