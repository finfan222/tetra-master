package com.finfan.server.repository;

import com.finfan.server.entity.dictionaries.TalentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalentRepository extends JpaRepository<TalentEntity, Long> {
}
