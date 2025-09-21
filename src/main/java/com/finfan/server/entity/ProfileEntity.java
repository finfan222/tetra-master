package com.finfan.server.entity;

import com.finfan.server.entity.dictionaries.TalentEntity;
import com.finfan.server.enums.CollectorRank;
import com.finfan.server.enums.PlayerStatus;
import com.finfan.server.enums.Portrait;
import com.finfan.server.enums.Rank;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Portrait portrait;
    private Long gil;
    private Integer wins;
    private Integer losses;
    private Integer draws;
    private Integer rating;
    @Enumerated(EnumType.STRING)
    private Rank rank;
    @Deprecated(since = "Не введен")
    @Enumerated(EnumType.STRING)
    private CollectorRank collectorRank;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "talent_id", referencedColumnName = "id")
    private TalentEntity talent;
    @ToString.Exclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> cards;
    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Transient
    private PlayerStatus status = PlayerStatus.FREE;
}
