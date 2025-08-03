package com.finfan.server.repository;

import com.finfan.server.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Query("SELECT count(*) FROM CardEntity card WHERE card.id = :cardId")
    int getCount(@Param("cardId") int cardId);

    @Query("SELECT e FROM CardEntity e WHERE profile.id = :id")
    List<CardEntity> findAllByProfileId(@Param("id") Long id);

}
