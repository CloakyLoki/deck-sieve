package com.cloakyLoki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String colorName;
    @Column(name = "color_qty")
    private Integer colorQty;
}