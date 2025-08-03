package com.finfan.server.repository;

import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTemplateRepository extends JpaRepository<CardTemplateEntity, Long> {
}
