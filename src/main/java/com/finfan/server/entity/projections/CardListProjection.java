package com.finfan.server.entity.projections;

import com.finfan.server.enums.AtkType;
import com.finfan.server.enums.DeckBuild;

public interface CardListProjection {

    Long getId();
    Long getCardId();
    Integer getAtk();
    AtkType getAtkType();
    Integer getPDef();
    Integer getMDef();
    Integer getAtkArrows();
    Boolean getBase();
    DeckBuild getBuild();

}
