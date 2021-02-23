package com.luisfelipedejesusm.apicrudpractice.Repositories;

import com.luisfelipedejesusm.apicrudpractice.Enums.ERole;
import com.luisfelipedejesusm.apicrudpractice.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
    Role findByName(String rolename);
}
