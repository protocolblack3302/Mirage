package com.mirage.poetry.Repo;


import com.mirage.poetry.Domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByUsername(String username);
}
