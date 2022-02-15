package com.mirage.poetry.Repo;

import com.mirage.poetry.Domains.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepo extends JpaRepository<Authority , Long> {
}
