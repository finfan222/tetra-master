package com.finfan.server.repository;

import com.finfan.server.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Optional<AccountEntity> findByName(String name);

    Page<AccountEntity> findAllByOnline(Boolean online, Pageable pageable);

}
