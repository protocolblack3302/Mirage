package com.mirage.poetry.Repo;


import com.mirage.poetry.Domains.Poet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoetRepo extends JpaRepository<Poet, Long> {
    Poet getByUser_Id(Long userId);

}

