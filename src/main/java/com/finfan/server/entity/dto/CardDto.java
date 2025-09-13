package com.finfan.server.entity.dto;

import com.finfan.server.enums.AtkType;
import com.finfan.server.enums.DeckBuild;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private long id;
    private long cardId;
    private int atk;
    private AtkType atkType;
    private int pDef;
    private int mDef;
    private int atkArrows;
    private boolean base;
    private DeckBuild build;

}
