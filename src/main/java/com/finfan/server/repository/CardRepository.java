package com.finfan.server.repository;

import com.finfan.server.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Query("SELECT count(*) FROM CardEntity card WHERE card.profile.id = :profileId AND card.template.id = :cardTemplateId")
    int getCountByProfileIdAndTemplateId(@Param("profileId") Long profileId, @Param("cardTemplateId") Long cardTemplateId);

    @Query("SELECT card FROM CardEntity card WHERE card.profile.id = :profileId")
    List<CardEntity> findAllByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT card FROM CardEntity card WHERE card.profile.id = :profileId AND card.template.id = :cardId")
    List<CardEntity> findAllByProfileIdAndCardId(@Param("profileId") Long profileId, @Param("cardId") Long cardId);

    @Query("SELECT card FROM CardEntity card WHERE card.profile.id = :profileId AND card.id in (:ids)")
    List<CardEntity> findAllByProfileIdAndWhereInIds(Long profileId, @Param("id") List<Long> ids);

}
