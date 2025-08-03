package com.finfan.server.repository;

import com.finfan.server.entity.AccountEntity;
import com.finfan.server.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    //@Query("SELECT e FROM Profile e WHERE e.account.id = :id")
    //ProfileEntity findByAccountId(@Param("id") Long id);

}
