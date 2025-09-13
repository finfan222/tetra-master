package com.finfan.server.mappers;

import com.finfan.server.entity.CardEntity;
import com.finfan.server.entity.dto.CardDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "rateLvlAtk", ignore = true)
    @Mapping(target = "rateLvlAtkTypeToA", ignore = true)
    @Mapping(target = "rateLvlAtkTypeToX", ignore = true)
    @Mapping(target = "rateLvlMDef", ignore = true)
    @Mapping(target = "rateLvlPDef", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "template", ignore = true)
    CardDto toDto(CardEntity entity);

    @Mapping(target = "rateLvlAtk", ignore = true)
    @Mapping(target = "rateLvlAtkTypeToA", ignore = true)
    @Mapping(target = "rateLvlAtkTypeToX", ignore = true)
    @Mapping(target = "rateLvlMDef", ignore = true)
    @Mapping(target = "rateLvlPDef", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "template", ignore = true)
    CardEntity toEntity(CardDto dto);
}
