package com.finfan.server.entity.dictionaries;

import com.finfan.server.enums.AtkType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card_templates")
public class CardTemplateEntity {

    @Id
    private Long id;
    private String name;
    private Integer atk;
    @Enumerated(EnumType.STRING)
    private AtkType atkType;
    private Integer pDef;
    private Integer mDef;
    private Float rateLvlAtk;
    private Float rateLvlAtkType;
    @Column(name = "rate_lvl_pdef")
    private Float rateLvlPDef;
    @Column(name = "rate_lvl_mdef")
    private Float rateLvlMDef;

}
