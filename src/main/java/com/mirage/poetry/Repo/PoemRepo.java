package com.mirage.poetry.Repo;

import com.mirage.poetry.Domains.Poem;
import com.mirage.poetry.Domains.Poet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PoemRepo extends JpaRepository<Poem, Long> {

    List<Poem> getAllByPoet_Id(Long id);
    @Modifying
    @Transactional
    Integer deleteByIdAndPoetId(Long id , Long poetId);

}
