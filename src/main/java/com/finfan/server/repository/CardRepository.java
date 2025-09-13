package com.finfan.server.repository;

import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.projections.CardCollectionProjection;
import com.finfan.server.entity.projections.CardListProjection;
import com.finfan.server.enums.DeckBuild;
import org.codehaus.commons.nullanalysis.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT card FROM CardEntity card WHERE card.profile.id = :profileId AND card.build = :build")
    List<CardEntity> findAllByProfileIdAndBuild(@Param("profileId") Long profileId, @Param("build") DeckBuild build);

    @Query(value = """
            SELECT
                c1.card_id as card_id,
                (SELECT COUNT(*) FROM cards c2 WHERE c2.profile_id = 1 AND c2.card_id = c1.card_id) as card_count
            FROM cards c1
            WHERE c1.profile_id = :profileId
            GROUP BY c1.card_id
            """, nativeQuery = true)
    List<CardCollectionProjection> findAllCardIdsAndTheirCountByProfileIdAsProjection(@Param("profileId") Long profileId);

    @Query("SELECT card FROM CardEntity card WHERE card.profile.id = :profileId AND card.template.id = :cardId")
    List<CardEntity> findAllByProfileIdAndCardId(@Param("profileId") Long profileId, @Param("cardId") Long cardId);

    @Query("""
            SELECT
                card.id as id,
                card.template.id as cardId,
                card.atk as atk,
                card.atkType as atkType,
                card.pDef as PDef,
                card.mDef as MDef,
                card.atkArrows as atkArrows,
                card.base as base,
                card.build as build
            FROM CardEntity card
            WHERE card.profile.id = :profileId
            """)
    List<CardListProjection> findAllByProfileIdAsProjection(@Param("profileId") Long profileId);

    @Query("""
            SELECT
                card.id as id,
                card.template.id as cardId,
                card.atk as atk,
                card.atkType as atkType,
                card.pDef as PDef,
                card.mDef as MDef,
                card.atkArrows as atkArrows,
                card.base as base,
                card.build as build
            FROM CardEntity card
            WHERE card.profile.id = :profileId AND card.template.id = :cardId
            """)
    List<CardListProjection> findAllByProfileIdAndCardIdAsProjection(@Param("profileId") Long profileId, @Param("cardId") Long cardId);


    @Query("SELECT card FROM CardEntity card WHERE card.profile.id = :profileId AND card.id in (:ids)")
    List<CardEntity> findAllByProfileIdAndWhereInIds(Long profileId, @Param("id") List<Long> ids);

    @Modifying
    @Query("DELETE FROM CardEntity card WHERE card.id in (:ids)")
    void deleteAllByIds(@NotNull @Param("ids") List<Long> ids);

    @Modifying
    @Query("""
            UPDATE CardEntity e SET e.build = CASE WHEN e.id IN (:modifyingIds) THEN :build ELSE NULL END WHERE e.profile.id = :profileId AND build = :build
            """)
    void refreshBuilds(@Param("profileId") Long profileId, @Param("build") DeckBuild build, @Param("modifyingIds") List<Long> modifyingIds);
}
