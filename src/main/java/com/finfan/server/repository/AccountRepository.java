package com.finfan.server.repository;

import com.finfan.server.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query("SELECT e FROM AccountEntity e WHERE e.name = :name")
    Optional<AccountEntity> findByName(@Param("name") String name);

}
