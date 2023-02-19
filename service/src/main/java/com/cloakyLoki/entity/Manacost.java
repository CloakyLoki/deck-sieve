package com.cloakyLoki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer id;
    private Integer red;
    private Integer black;
    private Integer green;
    private Integer blue;
    private Integer white;
    private Integer common;
}