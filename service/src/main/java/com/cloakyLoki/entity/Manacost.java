package com.cloakyLoki.entity;

import com.cloakyLoki.entity.enumerated.ColorIndicator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cards_manacost_qty")
public class Manacost {

    @Id
    @Column(name = "card_id")
    private Integer cardId;

    @Column(name = "color_name")
    @Enumerated(EnumType.STRING)
    private ColorIndicator colorName;

    @Column(name = "color_qty")
    private Integer colorQty;
}