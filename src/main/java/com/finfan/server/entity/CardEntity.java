package com.finfan.server.entity;

import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import com.finfan.server.enums.AtkType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer atkArrows;
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
    private Boolean base;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private CardTemplateEntity template;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private ProfileEntity profile;

}
