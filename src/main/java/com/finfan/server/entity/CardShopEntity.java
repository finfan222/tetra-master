package com.finfan.server.entity;

import com.finfan.server.entity.dictionaries.CardTemplateEntity;
import jakarta.persistence.Entity;
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
@Table(name = "shop_cards")
public class CardShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer atkArrows;
    private Integer price;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private CardTemplateEntity template;

}
